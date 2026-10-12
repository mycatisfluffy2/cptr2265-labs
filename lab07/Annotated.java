/*
 * CPTR 2265 - Lab 7, Part A starter: Campus Bookstore Order System
 * =================================================================
 *
 * This program runs (try it with Java 21:  java Annotated.java), but its
 * design is poor. It violates at least three of the five SOLID principles --
 * and one of those violations already causes a crash in the demo in main().
 *
 * Your tasks (see the Week 7 lab page for full details):
 *   1. Copy this file to lab07/original/ and annotate it: add a comment such as
 *          // SOLID VIOLATION (SRP): ...explanation...
 *      at every place you find a violation. Commit.
 *   2. Refactor the code (in lab07/refactored/) so it complies with SOLID.
 *      You may split it into several files. The program should still produce
 *      the same receipts and behavior. Commit.
 *
 * Hint: look at who is responsible for what, what you would have to edit to
 * add a new customer type or payment method, whether every subclass can
 * really be used where its parent is expected, whether every class needs
 * every method it is forced to implement, and which classes create their
 * own dependencies.
 */

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Annotated {

    public static void main(String[] args) {
        OrderProcessor processor = new OrderProcessor();

        Order order1 = new Order(1001, "Ana Lopez", "ana@student.mctc.edu", "student", "card");
        order1.addItem(new Item("TXT-2265", "Software Engineering 10e", 89.99, 1));
        order1.addItem(new Item("SUP-0042", "Engineering notebook", 6.50, 2));
        processor.process(order1);

        System.out.println();
        Order order2 = new Order(1002, "Dr. Kim", "kim@mctc.edu", "staff", "invoice");
        order2.addItem(new Item("SUP-0007", "Whiteboard markers", 12.00, 1));
        processor.process(order2);

        System.out.println();
        Order order3 = new DigitalOrder(1003, "Sam Ortiz", "sam@student.mctc.edu", "student", "campus_cash");
        order3.addItem(new Item("EBK-2265", "Software Engineering e-book", 54.00, 1));
        try {
            processor.process(order3);
        } catch (UnsupportedOperationException e) {
            // Something is wrong with the design if callers need this try/catch!
            System.out.println("*** Order 1003 crashed: " + e.getMessage());
        }
    }
}

// --------------------------------------------------------------------------
// Domain classes
// --------------------------------------------------------------------------

class Item {
    String sku;
    String name;
    double price;
    int quantity;

    Item(String sku, String name, double price, int quantity) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}

class Order {
    int orderId;
    String customerName;
    String customerEmail;
    String customerType;   // "student", "staff", "public"
    String paymentType;    // "card", "campus_cash", "invoice"
    List<Item> items = new ArrayList<>();

    Order(int orderId, String customerName, String customerEmail,
          String customerType, String paymentType) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerType = customerType;
        this.paymentType = paymentType;
    }

    void addItem(Item item) {
        items.add(item);
    }

    double subtotal() {
        double sum = 0;
        for (Item i : items) {
            sum += i.price * i.quantity;
        }
        return sum;
    }

    double shippingCost() {
        if (subtotal() >= 50) {
            return 0.0;
        }
        return 5.99;
    }
}

/** An order for e-books and access codes. Nothing physical ships. */
class DigitalOrder extends Order {
    DigitalOrder(int orderId, String customerName, String customerEmail,
                 String customerType, String paymentType) {
        super(orderId, customerName, customerEmail, customerType, paymentType);
    }

    @Override
    double shippingCost() {
        // SOLID VIOLATION (LSP): DigitalOrder is a subclass of Order, but it changes the behavior of shippingCost() to throw an exception.
        // This violates the Liskov Substitution Principle because code that works with Order cannot safely use DigitalOrder without special handling.
        throw new UnsupportedOperationException("Digital orders do not ship!");
    }
}

// --------------------------------------------------------------------------
// Notification
// --------------------------------------------------------------------------

/** Every notifier must support every channel. */
interface Notifier {
    void sendEmail(String to, String subject, String body);
    void sendSms(String phone, String message);
    void sendPush(String deviceId, String message);
}

class EmailNotifier implements Notifier {
    public void sendEmail(String to, String subject, String body) {
        System.out.println("[EMAIL to " + to + "] " + subject);
        System.out.println(body);
    }

    public void sendSms(String phone, String message) {
        // SOLID VIOLATION (ISP): EmailNotifier is forced to implement sendSms and sendPush 
        // even though it only supports email, violating the Interface Segregation Principle.
        // Should split the fat Notifier interface into smaller, single-purpose interfaces.
        throw new RuntimeException("EmailNotifier cannot send SMS");
    }

    public void sendPush(String deviceId, String message) {
        // SOLID VIOLATION (ISP): Same as above.
        // Should split the interface so classes only implement what they support.
        throw new RuntimeException("EmailNotifier cannot send push notifications");
    }
}

// --------------------------------------------------------------------------
// Storage
// --------------------------------------------------------------------------

class TextFileDatabase {
    private final String filename;

    TextFileDatabase(String filename) {
        this.filename = filename;
    }

    void saveLine(String line) {
        try (FileWriter fw = new FileWriter(filename, true)) {
            fw.write(line + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Could not save order: " + e.getMessage());
        }
    }
}

// --------------------------------------------------------------------------
// The order processor
// --------------------------------------------------------------------------

class OrderProcessor {
    private final TextFileDatabase database;
    private final EmailNotifier notifier;

    OrderProcessor() {
        // SOLID VIOLATION (DIP): OrderProcessor directly instantiates low-level concrete 
        // classes (TextFileDatabase and EmailNotifier) instead of depending on abstractions.
        // Should use dependency injection via the constructor and depend on interfaces instead of concrete classes.
        this.database = new TextFileDatabase("orders.txt");
        this.notifier = new EmailNotifier();
    }

    boolean process(Order order) {
        // SOLID VIOLATION (SRP): OrderProcessor handles validation, discounts, tax, 
        // shipping, payment processing, receipt formatting, persistence, and notifications 
        // all in a single class.
        // Should extract these responsibilities into dedicated classes (e.g., OrderValidator, ReceiptFormatter, DiscountPolicy).
        
        // ---- validate ----
        if (order.items.isEmpty()) {
            System.out.println("Order " + order.orderId + " rejected: no items");
            return false;
        }
        if (!order.customerEmail.contains("@")) {
            System.out.println("Order " + order.orderId + " rejected: bad email");
            return false;
        }
        for (Item item : order.items) {
            if (item.quantity <= 0 || item.price < 0) {
                System.out.println("Order " + order.orderId + " rejected: bad item " + item.sku);
                return false;
            }
        }

        // ---- discounts ----
        // SOLID VIOLATION (OCP & SRP): Hardcoded String checks for customer types require 
        // modifying this method whenever a new customer type is added.
        // Should use a Strategy pattern or polymorphism (e.g., DiscountPolicy interface) to handle discounts.
        double subtotal = order.subtotal();
        double discount;
        if (order.customerType.equals("student")) {
            discount = subtotal * 0.10;
        } else if (order.customerType.equals("staff")) {
            discount = subtotal * 0.15;
        } else if (order.customerType.equals("public")) {
            discount = 0.0;
        } else {
            throw new IllegalArgumentException("Unknown customer type: " + order.customerType);
        }

        // ---- shipping & tax ----
        double shipping = order.shippingCost();
        double tax = Math.round((subtotal - discount) * 0.07875 * 100.0) / 100.0;
        double total = Math.round((subtotal - discount + tax + shipping) * 100.0) / 100.0;

        // ---- payment ----
        // SOLID VIOLATION (OCP & SRP): Hardcoded String checks for payment types require 
        // modifying this method whenever a new payment method is added.
        // Should use a Strategy pattern with a factory instead of hardcoded conditionals.
        if (order.paymentType.equals("card")) {
            System.out.printf("Charging $%.2f to credit card on file...%n", total);
            double fee = Math.round(total * 0.029 * 100.0) / 100.0;
            System.out.printf("  Card processing fee recorded: $%.2f%n", fee);
        } else if (order.paymentType.equals("campus_cash")) {
            System.out.printf("Deducting $%.2f from campus cash account...%n", total);
        } else if (order.paymentType.equals("invoice")) {
            System.out.printf("Generating invoice for $%.2f, due in 30 days...%n", total);
        } else {
            throw new IllegalArgumentException("Unknown payment type: " + order.paymentType);
        }

        // ---- receipt formatting ----
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
        String receipt = sb.toString();

        // ---- persist ----
        database.saveLine(String.format("%d|%s|%s|%.2f",
                order.orderId, order.customerEmail, order.paymentType, total));

        // ---- notify ----
        notifier.sendEmail(order.customerEmail,
                "Your bookstore order #" + order.orderId, receipt);
        return true;
    }
}
