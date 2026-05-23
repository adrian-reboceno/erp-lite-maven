package com.ikigaisoftware.erp_lite.infrastructure.persistence.mongo.repository;

import com.ikigaisoftware.erp_lite.infrastructure.persistence.mongo.document.AuditLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends MongoRepository<AuditLogDocument, String> {
}