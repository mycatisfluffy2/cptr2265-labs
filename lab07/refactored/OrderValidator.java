class OrderValidator {
    public static boolean validate(Order order) {
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
        return true;
    }
}
