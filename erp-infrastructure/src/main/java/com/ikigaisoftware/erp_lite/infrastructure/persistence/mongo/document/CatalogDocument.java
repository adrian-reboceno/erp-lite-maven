package com.ikigaisoftware.erp_lite.infrastructure.persistence.mongo.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "catalogs")
public class CatalogDocument {

    /**
     * ID semántico definido por el negocio, ej: "catalog-product-categories".
     * No se autogenera — viene del cliente o del seed de datos.
     */
    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("description")
    private String description;

    /**
     * Enum almacenado como String en MongoDB.
     */
    @Indexed
    @Field("catalogType")
    private CatalogType catalogType;

    /**
     * Array embebido de items — no un String serializado.
     */
    @Field("items")
    private List<CatalogItem> items = new ArrayList<>();

    @Field("active")
    private Boolean active;

    @Field("createdAt")
    private Instant createdAt;

    @Field("updatedAt")
    private Instant updatedAt;
}