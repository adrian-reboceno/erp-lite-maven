package com.ikigaisoftware.erp_lite.domain.ports;

import com.ikigaisoftware.erp_lite.domain.order.OrderId;
import com.ikigaisoftware.erp_lite.domain.shared.Email;
import com.ikigaisoftware.erp_lite.domain.shared.Money;

public interface OrderConfirmEmailService {
    void sendMail(
            Email email,
            OrderId orderId,
            String orderNumber,
            Money money,
            String customerName,
            Integer itemsCount
    );
}
