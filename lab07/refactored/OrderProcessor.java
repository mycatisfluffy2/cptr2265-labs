public class OrderProcessor {
    private final OrderRepository repository;
    private final NotificationService notifier;

    public OrderProcessor(OrderRepository repository, NotificationService notifier) {
        this.repository = repository;
        this.notifier = notifier;
    }

    public boolean process(Order order) {
        if (!OrderValidator.validate(order)) {
            return false;
        }

        double subtotal = order.subtotal();
        DiscountPolicy discountPolicy = DiscountFactory.getPolicy(order.customerType);
        double discount = discountPolicy.calculateDiscount(subtotal);

        double shipping = order.shippingCost();
        double tax = Math.round((subtotal - discount) * 0.07875 * 100.0) / 100.0;
        double total = Math.round((subtotal - discount + tax + shipping) * 100.0) / 100.0;

        PaymentProcessorStrategy paymentProcessor = PaymentFactory.getProcessor(order.paymentType);
        paymentProcessor.process(total);

        String receipt = ReceiptFormatter.format(order, subtotal, discount, tax, shipping, total);

        repository.save(String.format("%d|%s|%s|%.2f",
                order.orderId, order.customerEmail, order.paymentType, total));

        notifier.sendEmail(order.customerEmail,
                "Your bookstore order #" + order.orderId, receipt);
        return true;
    }
}
