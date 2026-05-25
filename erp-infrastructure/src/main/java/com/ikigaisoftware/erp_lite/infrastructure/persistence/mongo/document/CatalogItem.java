package com.ikigaisoftware.erp_lite.infrastructure.persistence.mongo.document;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Objeto embebido dentro de CatalogDocument.items.
 * Usa record porque es inmutable y no tiene ciclo de vida propio.
 */
public record CatalogItem(

        @Field("id")
        String id,

        @Field("code")
        String code,

        @Field("value")
        String value,

        @Field("description")
        String description,

        @Field("displayOrder")
        Integer displayOrder,

        @Field("metadata")
        CatalogItemMetadata metadata
) {}