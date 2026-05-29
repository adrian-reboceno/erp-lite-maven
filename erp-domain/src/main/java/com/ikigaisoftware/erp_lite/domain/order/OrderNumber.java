package com.ikigaisoftware.erp_lite.domain.order;

import java.time.Year;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public record OrderNumber(String value) {

    private static final Pattern ORDER_NUMBER_PATTERN = Pattern.compile("^ORD-\\d{4}-\\d{3}$");
    private static final AtomicInteger sequence = new AtomicInteger(0);

    public OrderNumber {
        if (value == null) {
            throw new IllegalArgumentException("Order number cannot be null");
        }
        if (!ORDER_NUMBER_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException(
                    "Invalid OrderNumber format: '" + value + "'. Expected: ORD-YYYY-NNN (e.g., ORD-2025-001)");
        }
    }

    public static OrderNumber of(String value) {
        return new OrderNumber(value);
    }

    public static OrderNumber generate(int sequence) {
        if (sequence < 1 || sequence > 999) {
            throw new IllegalArgumentException("Sequence must be between 1 and 999");
        }
        int year = Year.now().getValue();
        String orderNumber = String.format("ORD-%d-%03d", year, sequence);
        return new OrderNumber(orderNumber);
    }
}
