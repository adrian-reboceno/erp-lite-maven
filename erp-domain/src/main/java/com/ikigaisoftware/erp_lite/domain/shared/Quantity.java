package com.ikigaisoftware.erp_lite.domain.shared;

import java.util.Objects;

public record Quantity(Integer value) {

    public Quantity {

        if (value == null) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }
        if (value <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0, got: " + value);
        }
    }

    public static Quantity of(int value) {
        return new Quantity(value);
    }
}
