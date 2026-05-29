package com.ikigaisoftware.erp_lite.domain.order;

import com.ikigaisoftware.erp_lite.domain.shared.CustomerId;

import java.util.Objects;

public record Customer(CustomerId customerId, String customerName) {

    public Customer {
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException("CustomerName cannot be null or blank");
        }
    }

    public static Customer of(CustomerId customerId, String customerName) {
        return new Customer(customerId, customerName);
    }
}
