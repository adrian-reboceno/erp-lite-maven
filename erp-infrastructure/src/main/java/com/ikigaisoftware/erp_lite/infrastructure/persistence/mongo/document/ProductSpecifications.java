package com.ikigaisoftware.erp_lite.infrastructure.persistence.mongo.document;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Especificaciones técnicas embebidas en ProductDocument.
 * Usa record porque es un value object inmutable.
 */
public record ProductSpecifications(

        @Field("processor")
        String processor,

        @Field("ram")
        String ram,

        @Field("storage")
        String storage,

        @Field("display")
        String display,

        @Field("weight")
        String weight
) {}