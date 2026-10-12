import java.time.LocalDate;

class ReceiptFormatter {
    public static String format(Order order, double subtotal, double discount, double tax, double shipping, double total) {
        StringBuilder sb = new StringBuilder();
        sb.append("=".repeat(40)).append("\n");
        sb.append("MCTC CAMPUS BOOKSTORE\n");
        sb.append("Order #").append(order.orderId).append("   ").append(LocalDate.now()).append("\n");
        sb.append("Customer: ").append(order.customerName)
          .append(" (").append(order.customerType).append(")\n");
        sb.append("-".repeat(40)).append("\n");
        for (Item item : order.items) {
            double lineTotal = item.price * item.quantity;
            sb.append(String.format("%d x %-26s $%8.2f%n", item.quantity, item.name, lineTotal));
        }
        sb.append("-".repeat(40)).append("\n");
        sb.append(String.format("%-30s $%8.2f%n", "Subtotal", subtotal));
        sb.append(String.format("%-29s -$%8.2f%n", "Discount", discount));
        sb.append(String.format("%-30s $%8.2f%n", "Tax", tax));
        sb.append(String.format("%-30s $%8.2f%n", "Shipping", shipping));
        sb.append(String.format("%-30s $%8.2f%n", "TOTAL", total));
        sb.append("=".repeat(40));
        return sb.toString();
    }
}
