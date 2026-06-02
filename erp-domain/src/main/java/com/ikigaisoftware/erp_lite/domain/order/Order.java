package com.ikigaisoftware.erp_lite.domain.order;

import com.ikigaisoftware.erp_lite.domain.common.AggregateRoot;
import com.ikigaisoftware.erp_lite.domain.order.events.OrderCancelled;
import com.ikigaisoftware.erp_lite.domain.order.events.OrderConfirmed;
import com.ikigaisoftware.erp_lite.domain.order.events.OrderCreated;
import com.ikigaisoftware.erp_lite.domain.order.events.OrderDelivered;
import com.ikigaisoftware.erp_lite.domain.order.events.OrderShipped;
import com.ikigaisoftware.erp_lite.domain.shared.AuditInfo;
import com.ikigaisoftware.erp_lite.domain.shared.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
public class Order extends AggregateRoot<OrderId> {

    private OrderNumber orderNumber;
    private Customer customer;
    private OrderStatus status;
    private List<OrderItem> items;
    private Money totalAmount;
    private AuditInfo auditInfo;

    protected Order() {
        super(null);
    }

    Order(OrderId id, OrderNumber orderNumber, Customer customer, OrderStatus status,
          List<OrderItem> items, Money totalAmount, AuditInfo auditInfo) {
        super(id);
        this.orderNumber = orderNumber;
        this.customer = customer;
        this.status = status;
        this.items = new ArrayList<>(items);
        this.totalAmount = totalAmount;
        this.auditInfo = auditInfo;
    }

    public static Order create(OrderNumber orderNumber, Customer customer,
                               List<OrderItem> items, String createdBy) {
        if (orderNumber == null) {
            throw new IllegalArgumentException("Order number cannot be null");
        }
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one item");
        }

        validateItems(items);

        OrderId id = OrderId.generate();
        AuditInfo audit = AuditInfo.create(createdBy, Instant.now());
        Order order = new Order(id, orderNumber, customer, OrderStatus.pending(), items, null, audit);
        order.calculateTotal();
        order.registerEvent(new OrderCreated(
                id, customer.customerId(), customer.customerName(), order.totalAmount, audit.createdAt()));
        return order;
    }

    public void confirm() {
        OrderStatus nextStatus = OrderStatus.confirmed();
        validateTransition(nextStatus);

        this.status = nextStatus;
        this.auditInfo = auditInfo.updateTimestamp();

        registerEvent(new OrderConfirmed(id, auditInfo.updatedAt()));
    }

    public void ship() {
        OrderStatus nextStatus = OrderStatus.confirmed();
        validateTransition(nextStatus);

        this.status = nextStatus;
        this.auditInfo = auditInfo.updateTimestamp();

        registerEvent(new OrderShipped(id, auditInfo.updatedAt()));
    }

    public void deliver() {
        OrderStatus nextStatus = OrderStatus.confirmed();
        validateTransition(nextStatus);

        this.status = nextStatus;
        this.auditInfo = auditInfo.updateTimestamp();

        registerEvent(new OrderDelivered(id, auditInfo.updatedAt()));
    }

    public void cancel(String reason) {

        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("Cancellation reason cannot be blank");
        }
        OrderStatus nextStatus = OrderStatus.cancelled();
        validateTransition(nextStatus);

        this.status = nextStatus;
        this.auditInfo = auditInfo.updateTimestamp();

        registerEvent(new OrderCancelled(id, reason, auditInfo.updatedAt()));
    }

    public void addItem(OrderItem item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        if (this.status.isPending()) {
            throw new IllegalStateException("Cannot add items to a " + status.value() + " order");
        }
        validateCurrencyConsistency(item.getUnitPrice().currency());
        this.items.add(item);
        calculateTotal();
        this.auditInfo = auditInfo.updateTimestamp();
    }

    public void removeItem(OrderItem item) {
        if (this.status.isPending()) {
            throw new IllegalStateException("Cannot remove items to a " + status.value() + " order");
        }

        if (!items.remove(item)) {
            throw new IllegalArgumentException("Item not found in this order");
        }
        if (items.isEmpty()) {
            throw new IllegalStateException("Order must have at least one item");
        }
        calculateTotal();
        this.auditInfo = auditInfo.updateTimestamp();
    }

    public void  calculateTotal() {
        //validateItems();
        Currency currency = items.getFirst().getUnitPrice().currency();
        this.totalAmount = items.stream()
                .peek(item -> validateCurrencyConsistency(item.getUnitPrice().currency(), currency))
                .map(OrderItem::getSubtotal)
                .reduce(Money.of(0.0, currency), Money::add);
    }

    private static  void validateItems(List<OrderItem> items) {
        if (items.isEmpty()) {
            throw new IllegalArgumentException("Order items cannot be empty");
        }

        java.util.Currency firstCurrency = items.get(0).getUnitPrice().currency();

        items.forEach(item -> {
            if (!item.getUnitPrice().currency().equals(firstCurrency)) {
                throw new IllegalArgumentException(
                        "All items must have the same currency. Expected: " + firstCurrency +
                                ", found: " + item.getUnitPrice().currency()
                );
            }

            // Validate subtotal calculation
            Money calculatedSubtotal = item.calculateSubtotal();
            if (!item.getSubtotal().equals(calculatedSubtotal)) {
                throw new IllegalArgumentException(
                        "Item subtotal mismatch. Expected: " + calculatedSubtotal +
                                ", found: " + item.getSubtotal()
                );
            }

        });
    }

    private void validateTransition(OrderStatus nextStatus) {
        if (!this.status.canTransitionTo(nextStatus)) {
            throw new IllegalStateException(
                    "Invalid status transition from " + this.status.value() +
                            " to " + nextStatus.value()
            );
        }
    }

    private void validateCurrencyConsistency(Currency itemCurrency) {
        if (totalAmount != null && !totalAmount.currency().equals(itemCurrency)) {
            throw new IllegalArgumentException(
                    "Currency mismatch: order uses " + totalAmount.currency() + " but item uses " + itemCurrency);
        }
    }

    private void validateCurrencyConsistency(Currency itemCurrency, Currency orderCurrency) {
        if (!orderCurrency.equals(itemCurrency)) {
            throw new IllegalArgumentException(
                    "All items must have the same currency. Expected " + orderCurrency + " but found " + itemCurrency);
        }
    }


}
