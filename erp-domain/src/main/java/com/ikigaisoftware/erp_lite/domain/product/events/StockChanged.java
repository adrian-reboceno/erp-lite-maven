package com.ikigaisoftware.erp_lite.domain.product.events;

import com.ikigaisoftware.erp_lite.domain.common.DomainEvent;
import com.ikigaisoftware.erp_lite.domain.product.ProductId;

import java.time.Instant;

public record StockChanged(
        ProductId productId,
        Integer oldStock,
        Integer newStock,
        String reason,
        Instant timestamp
) implements DomainEvent {}
