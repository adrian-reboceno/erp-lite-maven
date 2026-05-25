package com.ikigaisoftware.erp_lite.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(
        name = "order_products",
        schema = "public"
)
public class OrderProductEntity {

    @EqualsAndHashCode.Include
    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "order_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_order_products_order",
                    foreignKeyDefinition = "FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE"
            )
    )
    private OrderEntity order;

    /**
     * ON DELETE RESTRICT — no se borra el producto si tiene líneas de orden.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_order_products_product",
                    foreignKeyDefinition = "FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE RESTRICT"
            )
    )
    private ProductEntity product;

    /**
     * Snapshot del nombre al momento de crear la orden.
     */
    @Column(name = "product_name", nullable = false, length = 200)
    private String productName;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "subtotal", nullable = false, precision = 15, scale = 2)
    private BigDecimal subtotal;
}