package com.ikigaisoftware.erp_lite.domain.product;

import java.util.Objects;
import java.util.UUID;

public record ProductId(UUID value) {

    public ProductId {
        if (value == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
    }

    public static ProductId of(UUID value) {
        return new ProductId(value);
    }

    public static ProductId generate() {
        return new ProductId(UUID.randomUUID());
    }
}
