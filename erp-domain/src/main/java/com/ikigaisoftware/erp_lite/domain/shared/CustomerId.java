package com.ikigaisoftware.erp_lite.domain.shared;

import java.util.Objects;

public record CustomerId(Long value) {

    public CustomerId {
        if (value == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
        if (value <= 0) {
            throw new IllegalArgumentException("CustomerId must be positive, got: " + value);
        }
    }

    public static CustomerId of(Long value) {
        return new CustomerId(value);
    }
}
