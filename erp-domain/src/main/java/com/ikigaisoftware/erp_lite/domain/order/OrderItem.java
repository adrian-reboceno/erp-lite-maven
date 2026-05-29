package com.ikigaisoftware.erp_lite.domain.order;

import com.ikigaisoftware.erp_lite.domain.common.Entity;
import com.ikigaisoftware.erp_lite.domain.product.Product;
import com.ikigaisoftware.erp_lite.domain.product.ProductId;
import com.ikigaisoftware.erp_lite.domain.shared.Money;
import com.ikigaisoftware.erp_lite.domain.shared.Quantity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
public class OrderItem extends Entity<OrderItemId> {

    private OrderItemId id;
    private ProductId productReference;
    private String productName;
    private Quantity quantity;
    private Money unitPrice;
    private Money subtotal;

    protected OrderItem() {
        super(null);
    }

    public static OrderItem from(Product product, Quantity quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity == null) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }
        if (!product.isActive()){
            throw new IllegalStateException("Cannot order inactive product: " + product.getSku().value());
        }


        if (!product.hasAvailableStock(quantity.value())) {
            throw new IllegalStateException(
                    "Insufficient stock for product " + product.getSku().value() +
                    ": available=" + product.getStock().value() + ", requested=" + quantity.value());
        }

        Money unitPrice = product.getPrice();
        if (unitPrice.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("Product price must be greater than 0");
        }

        Money subtotal = unitPrice.multiply(quantity);
        return new OrderItem(
                OrderItemId.generate(),
                product.getId(),
                product.getName().value(),
                quantity,
                unitPrice,
                subtotal
        );
    }

    private OrderItem(
            OrderItemId id,
            ProductId productReference,
            String productName,
            Quantity quantity,
            Money unitPrice,
            Money subtotal
    ) {
        super(id);
        this.productReference = productReference;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
    }


    public Money calculateSubtotal() {
        return unitPrice.multiply(quantity);
    }
}
