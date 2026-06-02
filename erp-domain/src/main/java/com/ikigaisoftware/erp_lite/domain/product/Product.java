package com.ikigaisoftware.erp_lite.domain.product;

import com.ikigaisoftware.erp_lite.domain.common.AggregateRoot;
import com.ikigaisoftware.erp_lite.domain.product.events.ProductCreated;
import com.ikigaisoftware.erp_lite.domain.product.events.ProductDeactivated;
import com.ikigaisoftware.erp_lite.domain.product.events.ProductUpdated;
import com.ikigaisoftware.erp_lite.domain.product.events.StockChanged;
import com.ikigaisoftware.erp_lite.domain.shared.AuditInfo;
import com.ikigaisoftware.erp_lite.domain.shared.Money;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

import static lombok.AccessLevel.PROTECTED;

@Getter
public class Product extends AggregateRoot<ProductId> {

    private ProductId id;
    private SKU sku;
    private ProductName name;
    private String description;
    private Money price;
    private Stock stock;
    private CategoryReference category;
    private ProductImage image;
    private boolean active;
    private AuditInfo auditInfo;

    protected Product() {
        super(null);
    }


    private Product(
            ProductId id,
            SKU sku,
            ProductName name,
            String description,
            Money price,
            Stock stock,
            CategoryReference category,
            ProductImage image,
            boolean active,
            AuditInfo auditInfo
    ) {
        super(id);
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.image = image;
        this.active = active;
        this.auditInfo = auditInfo;
    }

    public static Product create(SKU sku, ProductName name, String description, Money price,
                                 Stock stock, CategoryReference category, ProductImage image,
                                 String createdBy) {
        validatePrice(price);
        ProductId id = ProductId.generate();
        Instant now = Instant.now();
        AuditInfo audit = AuditInfo.create(createdBy, Instant.now());
        Product product = new Product(id, sku, name, description, price, stock, category, image, true, audit);
        product.registerEvent(new ProductCreated(id, sku, name, price, now));
        return product;
    }

    public void update(ProductName newName, String newDescription, Money newPrice,
                       CategoryReference newCategory, ProductImage newImage) {
        validatePrice(newPrice);
        this.name = newName;
        this.description = newDescription;
        this.price = newPrice;
        this.category = newCategory;
        this.image = newImage;
        this.auditInfo = auditInfo.updateTimestamp();
        registerEvent(new ProductUpdated(id, Instant.now()));
    }

    public void incrementStock(int quantity, String reason) {
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("Reason for stock increment cannot be null or blank");
        }
        int oldValue = stock.value();
        this.stock = stock.increment(quantity);
        this.auditInfo = auditInfo.updateTimestamp();
        registerEvent(new StockChanged(id, oldValue, stock.value(), reason, Instant.now()));
    }

    public void decrementStock(int quantity, String reason) {
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("Reason for stock decrement cannot be null or blank");
        }
        int oldValue = stock.value();
        this.stock = stock.decrement(quantity);
        this.auditInfo = auditInfo.updateTimestamp();
        registerEvent(new StockChanged(id, oldValue, stock.value(), reason, Instant.now()));
    }

    public void changePrice(Money newPrice) {
        validatePrice(newPrice);
        this.price = newPrice;
        this.auditInfo = auditInfo.updateTimestamp();
        registerEvent(new ProductUpdated(id, Instant.now()));
    }

    public void deactivate() {
        if (!this.active) throw new IllegalStateException("Product is already inactive");
        this.active = false;
        this.auditInfo = auditInfo.updateTimestamp();
        registerEvent(new ProductDeactivated(id, Instant.now()));
    }

    public void activate() {
        if (this.active) throw new IllegalStateException("Product is already active");
        this.active = true;
        this.auditInfo = auditInfo.updateTimestamp();
        registerEvent(new ProductUpdated(id, Instant.now()));
    }

    public boolean hasAvailableStock(int requiredQuantity) {
        return stock.hasAvailable(requiredQuantity);
    }

    private static void validatePrice(Money price) {
        if (price == null) throw new IllegalArgumentException("Price cannot be null");
        if (price.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0, got: " + price.amount());
        }
    }
}
