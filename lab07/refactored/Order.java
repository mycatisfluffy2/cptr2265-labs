import java.util.ArrayList;
import java.util.List;

public abstract class Order {
    int orderId;
    String customerName;
    String customerEmail;
    String customerType;
    String paymentType;
    List<Item> items = new ArrayList<>();

    protected Order(int orderId, String customerName, String customerEmail,
                 String customerType, String paymentType) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerType = customerType;
        this.paymentType = paymentType;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public double subtotal() {
        double sum = 0;
        for (Item i : items) {
            sum += i.price * i.quantity;
        }
        return sum;
    }

    public abstract double shippingCost();
}

class PhysicalOrder extends Order {
    public PhysicalOrder(int orderId, String customerName, String customerEmail,
                         String customerType, String paymentType) {
        super(orderId, customerName, customerEmail, customerType, paymentType);
    }

    @Override
    public double shippingCost() {
        if (subtotal() >= 50) {
            return 0.0;
        }
        return 5.99;
    }
}

class DigitalOrder extends Order {
    public DigitalOrder(int orderId, String customerName, String customerEmail,
                        String customerType, String paymentType) {
        super(orderId, customerName, customerEmail, customerType, paymentType);
    }

    @Override
    public double shippingCost() {
        return 0.0;
    }
}
