package com.ikigaisoftware.erp_lite.domain.product;

import java.util.Objects;

public record CategoryReference(String categoryId) {

    public CategoryReference {
        if (categoryId == null) {
            throw new IllegalArgumentException("Category ID cannot be null");
        }
    }

    public static CategoryReference of(String categoryId) {
        return new CategoryReference(categoryId);
    }
}
