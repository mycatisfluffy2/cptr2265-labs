import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Notes & Logs — GitHub-inspired dark finance calculator.
 * Requires Java 17+ (Java 11 should also work).
 * Run: java NotesLogsApp.java  OR compile NotesLogsApp.java then run NotesLogsApp.
 */
public class NotesLogsApp {
    private static final Color BG = new Color(13, 17, 23);
    private static final Color PANEL = new Color(22, 27, 34);
    private static final Color PANEL2 = new Color(28, 33, 40);
    private static final Color BORDER = new Color(48, 54, 61);
    private static final Color TEXT = new Color(230, 237, 243);
    private static final Color MUTED = new Color(139, 148, 158);
    private static final Color BLUE = new Color(47, 129, 247);
    private final JFrame frame = new JFrame("Notes & Logs — Finance Workspace");
    private final JTextField expression = new JTextField();
    private final JLabel result = new JLabel("0", SwingConstants.RIGHT);
    private final DefaultListModel<String> historyModel = new DefaultListModel<>();
    private final JList<String> historyList = new JList<>(historyModel);
    private final JTextArea notes = new JTextArea();
    private final JTextField noteInput = new JTextField();
    private String current = "0";
    private String operator = "";
    private BigDecimal first = null;
    private boolean startNew = true;
    private final List<String> rawHistory = new ArrayList<>();
    private final DecimalFormat displayFormat = new DecimalFormat("#,##0.##########");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NotesLogsApp().show());
    }

    private void show() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(850, 560));
        frame.setSize(1080, 690);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(BG);
        frame.setLayout(new BorderLayout(0, 0));
        frame.add(buildHeader(), BorderLayout.NORTH);

        JPanel workspace = new JPanel(new GridBagLayout());
        workspace.setBackground(BG);
        workspace.setBorder(new EmptyBorder(12, 14, 14, 14));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 0, 12);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.12; c.weighty = 1;
        c.gridx = 0; c.gridy = 0;
        workspace.add(buildNotesPanel(), c);
        c.gridx = 1; c.weightx = 0.88; c.insets = new Insets(0, 0, 0, 0);
        workspace.add(buildRightColumn(), c);
        frame.add(workspace, BorderLayout.CENTER);
        installKeyboardSupport();
        frame.setVisible(true);
    }

    private JComponent buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PANEL);
        header.setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 0, BORDER),
                new EmptyBorder(13, 18, 13, 16)));
        JLabel title = new JLabel("\u25cf  Notes & Logs");
        title.setForeground(TEXT); title.setFont(new Font("SansSerif", Font.BOLD, 16));
        JLabel dots = new JLabel("\u25cf  \u25cf  \u25cf");
        dots.setForeground(MUTED); dots.setFont(new Font("SansSerif", Font.BOLD, 15));
        header.add(title, BorderLayout.WEST); header.add(dots, BorderLayout.EAST);
        return header;
    }

    private JPanel buildNotesPanel() {
        JPanel outer = panel();
        outer.setLayout(new BorderLayout(0, 10));
        outer.add(sectionTitle("Notes & Logs", "＋ Add Note"), BorderLayout.NORTH);

        notes.setText("• Monthly Budget Calculations\n\n• Check tax rate for next calculation\n\n• Review project estimates\n\n• Tax rate: 7.5%\n\n• Interest rate: 4.2%\n\n• Total Budget: $8,500.00\n");
        notes.setLineWrap(true); notes.setWrapStyleWord(true);
        notes.setFont(new Font("SansSerif", Font.PLAIN, 15));
        notes.setForeground(TEXT); notes.setCaretColor(TEXT); notes.setBackground(PANEL2);
        notes.setBorder(new EmptyBorder(12, 12, 12, 12));
        JScrollPane noteScroll = new JScrollPane(notes);
        styleScroll(noteScroll);
        outer.add(noteScroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout(8, 0));
        bottom.setOpaque(false);
        noteInput.setText("");
        noteInput.setToolTipText("Write a note, then press Save");
        styleField(noteInput);
        JButton save = button("Save", false);
        save.addActionListener(e -> appendNote());
        noteInput.addActionListener(e -> appendNote());
        JButton copy = button("Copy All", false);
        copy.addActionListener(e -> {
            notes.selectAll(); notes.copy(); notes.setCaretPosition(notes.getDocument().getLength());
        });
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        actions.setOpaque(false); actions.add(copy); actions.add(save);
        bottom.add(noteInput, BorderLayout.CENTER); bottom.add(actions, BorderLayout.SOUTH);
        outer.add(bottom, BorderLayout.SOUTH);
        return outer;
    }

    private JPanel buildRightColumn() {
        JPanel right = new JPanel(new GridBagLayout());
        right.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.fill = GridBagConstraints.BOTH; c.weightx = 1;
        c.insets = new Insets(0, 0, 10, 0); c.gridy = 0; c.weighty = 0.53;
        right.add(buildCalculatorPanel(), c);
        c.gridy = 1; c.weighty = 0.47; c.insets = new Insets(0, 0, 0, 0);
        right.add(buildHistoryPanel(), c);
        return right;
    }

    private JPanel buildCalculatorPanel() {
        JPanel p = panel(); p.setLayout(new BorderLayout(0, 10));
        p.add(sectionTitle("▾  Calculator", null), BorderLayout.NORTH);
        JPanel body = new JPanel(new BorderLayout(0, 8)); body.setOpaque(false);
        expression.setFont(new Font("Monospaced", Font.PLAIN, 15));
        styleField(expression); expression.setText("");
        expression.setToolTipText("Type an expression, e.g. 8500 * 0.075");
        JPanel display = new JPanel(new BorderLayout(8, 0));
        display.setBackground(BG); display.setBorder(new CompoundBorder(new LineBorder(BORDER), new EmptyBorder(9, 12, 9, 12)));
        JLabel exprLabel = new JLabel("Expression");
        exprLabel.setForeground(MUTED); exprLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        result.setForeground(TEXT); result.setFont(new Font("Monospaced", Font.BOLD, 27));
        JPanel displayStack = new JPanel(new BorderLayout(0, 4)); displayStack.setOpaque(false);
        displayStack.add(exprLabel, BorderLayout.NORTH); displayStack.add(result, BorderLayout.CENTER);
        display.add(displayStack, BorderLayout.CENTER);
        body.add(expression, BorderLayout.NORTH);
        body.add(display, BorderLayout.CENTER);
        JPanel keys = new JPanel(new GridLayout(4, 5, 6, 6)); keys.setOpaque(false);
        String[][] labels = {
            {"7","8","9","⌫","÷"},
            {"4","5","6","×","+"},
            {"1","2","3","−","="},
            {"C","0",".","(",")"}
        };
        for (String[] row : labels) for (String label : row) {
            JButton b = button(label, label.equals("="));
            b.addActionListener(e -> press(label));
            keys.add(b);
        }
        JPanel lower = new JPanel(new BorderLayout(0, 8)); lower.setOpaque(false);
        lower.add(body, BorderLayout.NORTH); lower.add(keys, BorderLayout.CENTER);
        p.add(lower, BorderLayout.CENTER);
        JButton addToNotes = button("Insert result into notes", false);
        addToNotes.addActionListener(e -> {
            String value = result.getText();
            notes.append("\n• " + expression.getText() + " = " + value + "\n");
        });
        p.add(addToNotes, BorderLayout.SOUTH);
        return p;
    }

    private JPanel buildHistoryPanel() {
        JPanel p = panel(); p.setLayout(new BorderLayout(0, 8));
        p.add(sectionTitle("▾  History", null), BorderLayout.NORTH);
        historyList.setBackground(PANEL2); historyList.setForeground(TEXT);
        historyList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        historyList.setSelectionBackground(new Color(31, 111, 235));
        historyList.setFixedCellHeight(29);
        historyList.setBorder(new EmptyBorder(4, 8, 4, 8));
        historyList.setCellRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean selected, boolean focus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, selected, focus);
                label.setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 0, BORDER), new EmptyBorder(5, 4, 5, 4)));
                label.setForeground(selected ? Color.WHITE : TEXT);
                label.setBackground(selected ? new Color(31, 111, 235) : PANEL2);
                return label;
            }
        });
        JScrollPane scroll = new JScrollPane(historyList);
        styleScroll(scroll);
        p.add(scroll, BorderLayout.CENTER);
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0)); controls.setOpaque(false);
        JButton clear = button("Clear History", false);
        clear.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(frame, "Clear all calculation history?", "Clear History",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                historyModel.clear(); rawHistory.clear();
            }
        });
        JButton use = button("Use selected", false);
        use.addActionListener(e -> {
            int i = historyList.getSelectedIndex();
            if (i >= 0) {
                String s = rawHistory.get(i);
                notes.append("\n• " + s + "\n");
            }
        });
        controls.add(use); controls.add(Box.createHorizontalStrut(8)); controls.add(clear);
        p.add(controls, BorderLayout.SOUTH);
        return p;
    }

    private JPanel sectionTitle(String text, String action) {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(PANEL);
        bar.setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 0, BORDER), new EmptyBorder(8, 10, 8, 10)));
        JLabel label = new JLabel(text); label.setForeground(TEXT); label.setFont(new Font("SansSerif", Font.BOLD, 14));
        bar.add(label, BorderLayout.WEST);
        if (action != null) {
            JButton add = button(action, false);
            add.addActionListener(e -> noteInput.requestFocusInWindow());
            bar.add(add, BorderLayout.EAST);
        }
        return bar;
    }

    private JPanel panel() {
        JPanel p = new JPanel();
        p.setBackground(PANEL);
        p.setBorder(new CompoundBorder(new LineBorder(BORDER), new EmptyBorder(10, 10, 10, 10)));
        return p;
    }

    private JButton button(String text, boolean primary) {
        JButton b = new JButton(text);
        b.setForeground(TEXT);
        b.setBackground(primary ? new Color(31, 111, 235) : PANEL2);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setBorder(new CompoundBorder(new LineBorder(primary ? BLUE : BORDER), new EmptyBorder(7, 9, 7, 9)));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(primary ? BLUE : new Color(45, 51, 59)); }
            @Override public void mouseExited(MouseEvent e) { b.setBackground(primary ? new Color(31, 111, 235) : PANEL2); }
        });
        return b;
    }

    private void styleField(JTextField f) {
        f.setBackground(BG); f.setForeground(TEXT); f.setCaretColor(TEXT);
        f.setFont(new Font("Monospaced", Font.PLAIN, 15));
        f.setBorder(new CompoundBorder(new LineBorder(BORDER), new EmptyBorder(8, 10, 8, 10)));
    }

    private void styleScroll(JScrollPane s) {
        s.setBorder(new LineBorder(BORDER));
        s.getViewport().setBackground(PANEL2);
        s.getVerticalScrollBar().setBackground(PANEL);
    }

    private void appendNote() {
        String value = noteInput.getText().trim();
        if (!value.isEmpty()) {
            if (!notes.getText().isEmpty() && !notes.getText().endsWith("\n")) notes.append("\n");
            notes.append("• " + value + "\n");
            noteInput.setText("");
        }
    }

    private void press(String key) {
        try {
            if (key.matches("\\d") || key.equals(".")) {
                String shown = expression.getText();
                // Start a fresh expression after a completed result, but keep the
                // operator already entered when the user is typing its next operand.
                if (shown.contains("=")) shown = "";
                String trimmed = shown.trim();
                boolean expectingOperand = trimmed.isEmpty() || trimmed.endsWith("+")
                        || trimmed.endsWith("-") || trimmed.endsWith("*")
                        || trimmed.endsWith("/") || trimmed.endsWith("(");
                if (startNew && !expectingOperand) shown = "";
                String operand = shown.trim();
                int lastSpace = Math.max(operand.lastIndexOf(' '), operand.lastIndexOf('('));
                String currentOperand = operand.substring(lastSpace + 1);
                if (key.equals(".") && currentOperand.contains(".")) return;
                if (key.equals(".") && (expectingOperand || startNew)) key = "0.";
                expression.setText(shown + key);
                current = key;
                startNew = false;
                result.setText(key);
            } else if (key.equals("C")) {
                current = "0"; operator = ""; first = null; startNew = true;
                expression.setText(""); result.setText("0");
            } else if (key.equals("⌫")) {
                String s = expression.getText();
                if (!s.isEmpty()) expression.setText(s.substring(0, s.length() - 1));
            } else if (key.equals("(") || key.equals(")")) {
                expression.setText(expression.getText() + key); startNew = false;
            } else if (key.equals("=")) {
                calculateExpression();
            } else {
                String op = key.equals("×") ? "*" : key.equals("÷") ? "/" : key.equals("−") ? "-" : key;
                String s = expression.getText().trim();
                if (s.isEmpty()) s = result.getText();
                if (op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/")) {
                    expression.setText(s + " " + op + " ");
                    startNew = true;
                }
            }
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void calculateExpression() {
        String source = expression.getText().trim();
        if (source.isEmpty()) return;
        try {
            BigDecimal value = new ExpressionParser(source).parse();
            String formatted = displayFormat.format(value.stripTrailingZeros());
            result.setText(formatted);
            String line = source + " = " + formatted;
            historyModel.add(0, line);
            rawHistory.add(0, line);
            expression.setText(source + " = " + formatted);
            current = value.stripTrailingZeros().toPlainString();
            startNew = true;
        } catch (Exception ex) {
            showError("Invalid expression");
        }
    }

    private void showError(String message) {
        result.setText("Error");
        Toolkit.getDefaultToolkit().beep();
    }

    private void installKeyboardSupport() {
        frame.getRootPane().registerKeyboardAction(e -> press("="), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        frame.getRootPane().registerKeyboardAction(e -> press("C"), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    private static class ExpressionParser {
        private final String s; private int pos = 0;
        ExpressionParser(String s) { this.s = s.replace("×", "*").replace("÷", "/").replace("−", "-"); }
        BigDecimal parse() {
            BigDecimal v = expression(); skip();
            if (pos != s.length()) throw new IllegalArgumentException();
            return v;
        }
        private BigDecimal expression() {
            BigDecimal v = term();
            while (true) {
                skip();
                if (take('+')) v = v.add(term());
                else if (take('-')) v = v.subtract(term());
                else return v;
            }
        }
        private BigDecimal term() {
            BigDecimal v = factor();
            while (true) {
                skip();
                if (take('*')) v = v.multiply(factor());
                else if (take('/')) v = v.divide(factor(), MathContext.DECIMAL64);
                else return v;
            }
        }
        private BigDecimal factor() {
            skip();
            if (take('+')) return factor();
            if (take('-')) return factor().negate();
            if (take('(')) {
                BigDecimal v = expression(); skip();
                if (!take(')')) throw new IllegalArgumentException();
                return v;
            }
            int start = pos;
            while (pos < s.length() && (Character.isDigit(s.charAt(pos)) || s.charAt(pos) == '.')) pos++;
            if (start == pos) throw new IllegalArgumentException();
            return new BigDecimal(s.substring(start, pos));
        }
        private void skip() { while (pos < s.length() && Character.isWhitespace(s.charAt(pos))) pos++; }
        private boolean take(char c) { if (pos < s.length() && s.charAt(pos) == c) { pos++; return true; } return false; }
    }
}
