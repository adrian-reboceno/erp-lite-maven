package com.ikigaisoftware.erp_lite.domain.product;

import java.util.Objects;

public record Stock(Integer value) {

    public Stock {
        if (value == null) {
            throw new IllegalArgumentException("Stock cannot be null");
        }
        if (value < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
    }

    public static Stock of(int value) {
        return new Stock(value);
    }

    public static Stock zero() {
        return new Stock(0);
    }

    public Stock increment(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Increment quantity cannot be negative");
        }
        return new Stock(this.value + quantity);
    }

    public Stock decrement(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Decrement quantity cannot be negative");
        }
        int newValue = this.value - quantity;
        if (newValue < 0) {
            throw new IllegalArgumentException("Cannot decrement stock below zero. Current: " + this.value + ", requested: " + quantity);
        }
        return new Stock(newValue);
    }

    public boolean hasAvailable(int required) {
        return value >= required;
    }
}
