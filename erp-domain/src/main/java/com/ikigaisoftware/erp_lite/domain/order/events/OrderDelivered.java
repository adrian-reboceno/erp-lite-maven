package com.ikigaisoftware.erp_lite.domain.order.events;

import com.ikigaisoftware.erp_lite.domain.common.DomainEvent;
import com.ikigaisoftware.erp_lite.domain.order.OrderId;

import java.time.Instant;

public record OrderDelivered(
        OrderId orderId,
        Instant timestamp
) implements DomainEvent {}
