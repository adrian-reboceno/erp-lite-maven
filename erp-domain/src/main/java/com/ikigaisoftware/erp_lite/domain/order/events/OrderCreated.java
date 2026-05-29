package com.ikigaisoftware.erp_lite.domain.order.events;

import com.ikigaisoftware.erp_lite.domain.common.DomainEvent;
import com.ikigaisoftware.erp_lite.domain.order.OrderId;
import com.ikigaisoftware.erp_lite.domain.shared.CustomerId;
import com.ikigaisoftware.erp_lite.domain.shared.Money;

import java.time.Instant;

public record OrderCreated(
        OrderId orderId,
        CustomerId customerId,
        String customerName,
        Money totalAmount,
        Instant timestamp
) implements DomainEvent {}
