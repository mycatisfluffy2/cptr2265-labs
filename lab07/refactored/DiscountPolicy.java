

interface DiscountPolicy {
    double calculateDiscount(double subtotal);
}

class StudentDiscount implements DiscountPolicy {
    public double calculateDiscount(double subtotal) {
        return subtotal * 0.10;
    }
}

class StaffDiscount implements DiscountPolicy {
    public double calculateDiscount(double subtotal) {
        return subtotal * 0.15;
    }
}

class PublicDiscount implements DiscountPolicy {
    public double calculateDiscount(double subtotal) {
        return 0.0;
    }
}

class DiscountFactory {
    public static DiscountPolicy getPolicy(String customerType) {
        return switch (customerType) {
            case "student" -> new StudentDiscount();
            case "staff" -> new StaffDiscount();
            case "public" -> new PublicDiscount();
            default -> throw new IllegalArgumentException("Unknown customer type: " + customerType);
        };
    }
}
