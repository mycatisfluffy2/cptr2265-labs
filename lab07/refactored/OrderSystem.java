public class OrderSystem {
    public static void main(String[] args) {
        OrderProcessor processor = new OrderProcessor(
            new TextFileOrderRepository("orders.txt"),
            new EmailNotificationService()
        );

        Order order1 = new PhysicalOrder(1001, "Ana Lopez", "ana@student.mctc.edu", "student", "card");
        order1.addItem(new Item("TXT-2265", "Software Engineering 10e", 89.99, 1));
        order1.addItem(new Item("SUP-0042", "Engineering notebook", 6.50, 2));
        processor.process(order1);

        System.out.println();
        Order order2 = new PhysicalOrder(1002, "Dr. Kim", "kim@mctc.edu", "staff", "invoice");
        order2.addItem(new Item("SUP-0007", "Whiteboard markers", 12.00, 1));
        processor.process(order2);

        System.out.println();
        Order order3 = new DigitalOrder(1003, "Sam Ortiz", "sam@student.mctc.edu", "student", "campus_cash");
        order3.addItem(new Item("EBK-2265", "Software Engineering e-book", 54.00, 1));
        try {
            processor.process(order3);
        } catch (UnsupportedOperationException e) {
            System.out.println("*** Order 1003 crashed: " + e.getMessage());
        }
    }
}
