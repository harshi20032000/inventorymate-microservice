package com.harshi_solution.audit_trail.mongo.repository;

import java.time.Instant;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.harshi_solution.audit_trail.mongo.document.AuditLogDocument;

@Repository
@ConditionalOnProperty(name = "audit.mongodb.enabled", havingValue = "true", matchIfMissing = false)
public interface AuditLogMongoRepository
        extends MongoRepository<AuditLogDocument, String> {

    Page<AuditLogDocument> findByUsernameOrderByTimestampDesc(
            String username, Pageable pageable);

    Page<AuditLogDocument> findByServiceNameOrderByTimestampDesc(
            String serviceName, Pageable pageable);

    Page<AuditLogDocument> findByRoleOrderByTimestampDesc(
            String role, Pageable pageable);

    Page<AuditLogDocument> findByTimestampBetweenOrderByTimestampDesc(
            Instant from, Instant to, Pageable pageable);

    Page<AuditLogDocument> findByIsErrorTrueOrderByTimestampDesc(
            Pageable pageable);

    Page<AuditLogDocument> findByUsernameAndServiceNameOrderByTimestampDesc(
            String username, String serviceName, Pageable pageable);

    long countByUsernameAndTimestampBetween(
            String username, Instant from, Instant to);
}