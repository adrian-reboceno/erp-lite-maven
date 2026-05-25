package com.ikigaisoftware.erp_lite.infrastructure.persistence.mongo.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "audit_logs")
public class AuditLogDocument {

    /**
     * ObjectId generado por MongoDB — se mapea como String.
     */
    @Id
    private String id;

    @Field("userId")
    private String userId;

    @Field("className")
    private String className;

    @Field("methodName")
    private String methodName;

    @Field("endpoint")
    private String endpoint;

    @Field("ipAddress")
    private String ipAddress;

    @Field("executionTimeMs")
    private Long executionTimeMs;

    @Field("success")
    private Boolean success;

    /**
     * Mensaje de error — null cuando success = true.
     */
    @Field("errorMessage")
    private String errorMessage;

    @Indexed
    @Field("timestamp")
    private Instant timestamp;
}