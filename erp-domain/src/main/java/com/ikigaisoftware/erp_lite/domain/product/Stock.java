package com.ikigaisoftware.erp_lite.domain.product;

import java.util.Objects;

public record Stock(Integer value) {

    public Stock {
        if (value == null) {
            throw new IllegalArgumentException("Stock cannot be null");
        }
        if (value < 0) {
            throw new IllegalArgumentException("Stock cannot be negative, got: " + value);
        }
    }

    public static Stock of(int value) {
        return new Stock(value);
    }

    public static Stock zero() {
        return new Stock(0);
    }

    public Stock increment(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Increment quantity must be positive, got: " + quantity);
        }
        return new Stock(value + quantity);
    }

    public Stock decrement(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Decrement quantity must be positive, got: " + quantity);
        }
        if (value - quantity < 0) {
            throw new IllegalArgumentException(
                    "Insufficient stock: available=" + value + ", requested=" + quantity);
        }
        return new Stock(value - quantity);
    }

    public boolean hasAvailable(int required) {
        return value >= required;
    }
}
