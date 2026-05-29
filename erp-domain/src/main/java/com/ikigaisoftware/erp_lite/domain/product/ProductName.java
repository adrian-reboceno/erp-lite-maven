package com.ikigaisoftware.erp_lite.domain.product;

import java.util.Objects;

public record ProductName(String value) {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 200;

    public ProductName {
        if (value == null) {
            throw new IllegalArgumentException("Product name cannot be null");
        }
        if (value.length() < MIN_LENGTH) {
            throw new IllegalArgumentException("Product name must have at least " + MIN_LENGTH + " characters");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Product name cannot exceed " + MAX_LENGTH + " characters");
        }
    }

    public static ProductName of(String value) {
        return new ProductName(value);
    }
}
