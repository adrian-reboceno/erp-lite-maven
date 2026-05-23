package com.ikigaisoftware.erp_lite.infrastructure.persistence.mongo.document;

import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.List;

/**
 * Metadata embebida dentro de CatalogItem.
 * Los campos son nullable dependiendo del tipo de catálogo:
 * - PRODUCT_CATEGORIES → icon, color
 * - ORDER_STATUSES     → color, nextStatuses
 * - PAYMENT_METHODS    → icon, fee
 */
public record CatalogItemMetadata(

        @Field("icon")
        String icon,

        @Field("color")
        String color,

        @Field("nextStatuses")
        List<String> nextStatuses,

        @Field("fee")
        BigDecimal fee
) {}