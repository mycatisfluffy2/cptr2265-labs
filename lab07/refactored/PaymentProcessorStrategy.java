interface PaymentProcessorStrategy {
    void process(double total);
}

class CardPayment implements PaymentProcessorStrategy {
    public void process(double total) {
        System.out.printf("Charging $%.2f to credit card on file...%n", total);
        double fee = Math.round(total * 0.029 * 100.0) / 100.0;
        System.out.printf("  Card processing fee recorded: $%.2f%n", fee);
    }
}

class CampusCashPayment implements PaymentProcessorStrategy {
    public void process(double total) {
        System.out.printf("Deducting $%.2f from campus cash account...%n", total);
    }
}

class InvoicePayment implements PaymentProcessorStrategy {
    public void process(double total) {
        System.out.printf("Generating invoice for $%.2f, due in 30 days...%n", total);
    }
}

class PaymentFactory {
    public static PaymentProcessorStrategy getProcessor(String paymentType) {
        return switch (paymentType) {
            case "card" -> new CardPayment();
            case "campus_cash" -> new CampusCashPayment();
            case "invoice" -> new InvoicePayment();
            default -> throw new IllegalArgumentException("Unknown payment type: " + paymentType);
        };
    }
}
