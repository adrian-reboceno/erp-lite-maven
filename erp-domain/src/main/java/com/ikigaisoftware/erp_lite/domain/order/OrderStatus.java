package com.ikigaisoftware.erp_lite.domain.order;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public record OrderStatus(String value) {


    public static final String PENDING = "PENDING";
    public static final String CONFIRMED = "CONFIRMED";
    public static final String SHIPPED = "SHIPPED";
    public static final String DELIVERED = "DELIVERED";
    public static final String CANCELLED = "CANCELLED";

    // PENDING -> CONFIRMED | CANCELLED
    // CONFIRMED -> SHIPPED | CANCELLED
    // SHIPPED -> DELIVERED
    // DELIVERED -> (final)
    // CANCELLED -> (final)

    private static final Set<String> VALID_STATUSES = Set.of(
            PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
    );

    private static final Set<String> FINAL_STATES = Set.of(DELIVERED, CANCELLED);

    public OrderStatus {
        if (value == null) {
            throw new IllegalArgumentException("Order status cannot be null");
        }
        if (!VALID_STATUSES.contains(value)) {
            throw new IllegalArgumentException("Invalid order status: " + value + ". Valid values: " + VALID_STATUSES);
        }
    }

    public static OrderStatus of(String value) { return new OrderStatus(value); }
    public static OrderStatus pending()   { return new OrderStatus("PENDING"); }
    public static OrderStatus confirmed() { return new OrderStatus("CONFIRMED"); }
    public static OrderStatus shipped()   { return new OrderStatus("SHIPPED"); }
    public static OrderStatus delivered() { return new OrderStatus("DELIVERED"); }
    public static OrderStatus cancelled() { return new OrderStatus("CANCELLED"); }

    public boolean canTransitionTo(OrderStatus nextStatus) {
        return switch (this.value) {
            case PENDING -> CONFIRMED.equals(nextStatus.value) || CANCELLED.equals(nextStatus.value);
            case CONFIRMED -> SHIPPED.equals(nextStatus.value) || CANCELLED.equals(nextStatus.value);
            case SHIPPED -> DELIVERED.equals(nextStatus.value);
            case DELIVERED, CANCELLED -> false; // Final states
            default -> false;
        };
    }

    public boolean isPending()   { return "PENDING".equals(value); }
    public boolean isConfirmed() { return "CONFIRMED".equals(value); }
    public boolean isShipped()   { return "SHIPPED".equals(value); }
    public boolean isDelivered() { return "DELIVERED".equals(value); }
    public boolean isCancelled() { return "CANCELLED".equals(value); }
    public boolean isFinalState() { return isDelivered() || isCancelled(); }
}
