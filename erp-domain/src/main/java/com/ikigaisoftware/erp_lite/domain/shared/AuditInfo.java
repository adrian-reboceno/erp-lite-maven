package com.ikigaisoftware.erp_lite.domain.shared;

import java.time.Instant;
import java.util.Objects;

public record AuditInfo(String createdBy, Instant createdAt, Instant updatedAt) {

    public AuditInfo {
        if (createdBy == null || createdBy.isBlank()) {
            throw new IllegalArgumentException("Created by cannot be null or blank");
        }
        if (createdAt == null) {
            throw new IllegalArgumentException("Created at cannot be null");
        }
        if (updatedAt == null) {
            throw new IllegalArgumentException("Updated at cannot be null");
        }
        if (updatedAt.isBefore(createdAt)) {
            throw new IllegalArgumentException("Updated at cannot be before created at");
        }
    }

    public static AuditInfo create(String createdBy, Instant timestamp) {
        return new AuditInfo(createdBy, timestamp, timestamp);
    }

    public AuditInfo updateTimestamp() {
        return new AuditInfo(createdBy, createdAt, Instant.now());
    }
}
