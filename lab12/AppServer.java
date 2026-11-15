import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class AppServer {

    private static String env(String name) {
        String v = System.getenv(name);
        if (v == null || v.isBlank()) {
            throw new IllegalStateException("Missing required environment variable: " + name);
        }
        return v;
    }

    private static final String PORT = env("PORT");
    private static final String DATABASE_URL = env("DATABASE_URL");
    private static final String DATABASE_USER = env("DATABASE_USER");
    private static final String DATABASE_PASSWORD = env("DATABASE_PASSWORD");

    private static volatile boolean dbReady = false;

    public static void main(String[] args) throws IOException {
        initDatabase();
        HttpServer server = HttpServer.create(new InetSocketAddress(Integer.parseInt(PORT)), 0);
        server.createContext("/health", AppServer::handleHealth);
        server.createContext("/calculate", AppServer::handleCalculate);
        server.createContext("/history", AppServer::handleHistory);
        server.createContext("/", AppServer::handleRoot);
        server.setExecutor(java.util.concurrent.Executors.newFixedThreadPool(8));
        server.start();
        System.out.println("Notes & Logs API listening on port " + PORT);
    }

    private static Connection connect() throws Exception {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }

    private static void initDatabase() {
        for (int attempt = 1; attempt <= 30; attempt++) {
            try (Connection c = connect(); Statement st = c.createStatement()) {
                st.execute("CREATE TABLE IF NOT EXISTS calculations ("
                        + "id BIGSERIAL PRIMARY KEY, "
                        + "expression TEXT NOT NULL, "
                        + "result TEXT NOT NULL, "
                        + "created_at TIMESTAMPTZ NOT NULL DEFAULT now())");
                dbReady = true;
                System.out.println("Database ready (" + DATABASE_URL + ")");
                return;
            } catch (Exception e) {
                System.err.println("DB connect attempt " + attempt + " failed: " + e.getMessage());
                try { Thread.sleep(2000); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); return; }
            }
        }
        System.err.println("Database unavailable at startup - serving in degraded mode.");
    }

    private static void handleRoot(HttpExchange ex) throws IOException {
        if (!"/".equals(ex.getRequestURI().getPath())) {
            respond(ex, 404, "{\"error\":\"not found\"}");
            return;
        }
        respond(ex, 200, "{\"service\":\"notes-logs\",\"endpoints\":[\"/health\",\"/calculate?expr=2%2B2\",\"/history\"]}");
    }

    private static void handleHealth(HttpExchange ex) throws IOException {
        boolean up = false;
        try (Connection c = connect(); Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT 1")) {
            up = rs.next();
        } catch (Exception ignored) { }
        dbReady = up;
        String json = "{\"status\":\"" + (up ? "ok" : "degraded") + "\",\"database\":\"" + (up ? "up" : "down") + "\"}";
        respond(ex, up ? 200 : 503, json);
    }

    private static void handleCalculate(HttpExchange ex) throws IOException {
        String expr = queryParam(ex, "expr");
        if (expr == null || expr.isBlank()) {
            respond(ex, 400, "{\"error\":\"missing required query parameter: expr\"}");
            return;
        }
        try {
            String value = new ExpressionParser(expr).parse().stripTrailingZeros().toPlainString();
            String saved = "saved";
            try (Connection c = connect();
                 PreparedStatement ps = c.prepareStatement(
                         "INSERT INTO calculations (expression, result) VALUES (?, ?)")) {
                ps.setString(1, expr);
                ps.setString(2, value);
                ps.executeUpdate();
            } catch (Exception e) {
                saved = "not-saved";
            }
            respond(ex, 200, "{\"expression\":\"" + json(expr) + "\",\"result\":\"" + json(value) + "\",\"storage\":\"" + saved + "\"}");
        } catch (Exception e) {
            respond(ex, 400, "{\"error\":\"invalid expression\"}");
        }
    }

    private static void handleHistory(HttpExchange ex) throws IOException {
        List<String> items = new ArrayList<>();
        try (Connection c = connect();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT expression, result, created_at FROM calculations ORDER BY id DESC LIMIT 20");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                items.add("{\"expression\":\"" + json(rs.getString(1))
                        + "\",\"result\":\"" + json(rs.getString(2))
                        + "\",\"created_at\":\"" + rs.getString(3) + "\"}");
            }
        } catch (Exception e) {
            respond(ex, 503, "{\"error\":\"database unavailable\"}");
            return;
        }
        respond(ex, 200, "{\"count\":" + items.size() + ",\"items\":[" + String.join(",", items) + "]}");
    }

    private static String queryParam(HttpExchange ex, String name) {
        String q = ex.getRequestURI().getRawQuery();
        if (q == null) return null;
        for (String pair : q.split("&")) {
            int i = pair.indexOf('=');
            if (i < 0) continue;
            if (name.equals(URLDecoder.decode(pair.substring(0, i), StandardCharsets.UTF_8))) {
                return URLDecoder.decode(pair.substring(i + 1), StandardCharsets.UTF_8);
            }
        }
        return null;
    }

    private static String json(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
    }

    private static void respond(HttpExchange ex, int status, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().add("Content-Type", "application/json");
        ex.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = ex.getResponseBody()) { os.write(bytes); }
    }

    /** Same BigDecimal expression engine used by the Swing calculator. */
    static class ExpressionParser {
        private final String s;
        private int pos = 0;
        ExpressionParser(String s) {
            this.s = s.replace("×", "*").replace("÷", "/").replace("−", "-");
        }
        java.math.BigDecimal parse() {
            java.math.BigDecimal v = expression();
            skip();
            if (pos != s.length()) throw new IllegalArgumentException("Unexpected token");
            return v;
        }
        private java.math.BigDecimal expression() {
            java.math.BigDecimal v = term();
            while (true) {
                skip();
                if (take('+')) v = v.add(term());
                else if (take('-')) v = v.subtract(term());
                else return v;
            }
        }
        private java.math.BigDecimal term() {
            java.math.BigDecimal v = factor();
            while (true) {
                skip();
                if (take('*')) v = v.multiply(factor());
                else if (take('/')) v = v.divide(factor(), java.math.MathContext.DECIMAL64);
                else return v;
            }
        }
        private java.math.BigDecimal factor() {
            skip();
            if (take('+')) return factor();
            if (take('-')) return factor().negate();
            if (take('(')) {
                java.math.BigDecimal v = expression();
                skip();
                if (!take(')')) throw new IllegalArgumentException("Missing )");
                return v;
            }
            int start = pos;
            while (pos < s.length() && (Character.isDigit(s.charAt(pos)) || s.charAt(pos) == '.')) pos++;
            if (start == pos) throw new IllegalArgumentException("Expected number");
            return new java.math.BigDecimal(s.substring(start, pos));
        }
        private void skip() { while (pos < s.length() && Character.isWhitespace(s.charAt(pos))) pos++; }
        private boolean take(char c) { if (pos < s.length() && s.charAt(pos) == c) { pos++; return true; } return false; }
    }
}
