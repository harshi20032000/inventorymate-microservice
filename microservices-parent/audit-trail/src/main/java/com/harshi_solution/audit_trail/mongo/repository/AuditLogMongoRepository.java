package com.harshi_solution.audit_trail.mongo.repository;


import java.time.Instant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.harshi_solution.audit_trail.mongo.document.AuditLogDocument;

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