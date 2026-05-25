package com.ikigaisoftware.erp_lite.infrastructure.persistence.mongo.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "product_documents")
public class ProductDocument {

    /**
     * UUID del producto — mismo ID que en la tabla JPA products.
     * Sirve como puente entre el modelo relacional y el documental.
     */
    @Id
    private String id;

    @Indexed(unique = true)
    @Field("sku")
    private String sku;

    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @Field("price")
    private BigDecimal price;

    @Field("currency")
    private String currency;

    @Field("stock")
    private Integer stock;

    @Field("categoryId")
    private String categoryId;

    @Field("categoryName")
    private String categoryName;

    @Field("imageUrl")
    private String imageUrl;

    /**
     * Objeto embebido con especificaciones técnicas del producto.
     */
    @Field("specifications")
    private ProductSpecifications specifications;

    /**
     * Tags para búsqueda full-text y filtrado.
     */
    @Field("tags")
    private List<String> tags = new ArrayList<>();

    @Field("active")
    private Boolean active;

    @Field("createdAt")
    private Instant createdAt;

    @Field("updatedAt")
    private Instant updatedAt;
}