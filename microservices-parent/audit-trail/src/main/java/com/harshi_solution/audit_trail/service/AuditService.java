package com.harshi_solution.audit_trail.service;

import java.time.Instant;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harshi_solution.audit_trail.dto.AuditEventDTO;
import com.harshi_solution.audit_trail.mongo.document.AuditLogDocument;
import com.harshi_solution.audit_trail.mongo.repository.AuditLogMongoRepository;
import com.harshi_solution.audit_trail.postgres.entity.ApiCallLog;
import com.harshi_solution.audit_trail.postgres.repository.ApiCallLogRepository;

@Service
public class AuditService {

    private static final Logger log = LoggerFactory.getLogger(AuditService.class);

    @Autowired(required = false)
    private AuditLogMongoRepository mongoRepo;
    private final ApiCallLogRepository pgRepo;

    public AuditService(
            ApiCallLogRepository pgRepo) {
        this.pgRepo = pgRepo;
    }

    private boolean isMongoEnabled() {
        return mongoRepo != null;
    }

    @Transactional
    public void record(AuditEventDTO event) {
        LocalDateTime timestamp = event.getTimestamp() != null
                ? event.getTimestamp()
                : LocalDateTime.now();
        // write to MongoDB only if enabled
        if (isMongoEnabled()) {
            AuditLogDocument doc = new AuditLogDocument();
            doc.setUsername(event.getUsername());
            doc.setRole(event.getRole());
            doc.setServiceName(event.getServiceName());
            doc.setMethod(event.getMethod());
            doc.setEndpoint(event.getUrl());
            doc.setResponseStatus(event.getResponseStatus());
            doc.setDurationMs(event.getDurationMs());
            doc.setUserAgent(event.getUserAgent());
            doc.setTimestamp(timestamp);
            doc.setError(event.getResponseStatus() != null
                    && event.getResponseStatus() >= 400);
            try {
                mongoRepo.save(doc);
            } catch (Exception e) {
                log.error("MongoDB write failed: {}", e.getMessage());
            }
        } else {
            log.debug("MongoDB disabled — skipping raw log for {} {}",
                    event.getUsername(), event.getUrl());
        }
        try {
            ApiCallLog logEntry = mapToEntity(event, timestamp);
            pgRepo.save(logEntry);

            log.debug("Audit log recorded for user={}, service={}, endpoint={}",
                    event.getUsername(),
                    event.getServiceName(),
                    event.getUrl());

        } catch (Exception e) {
            log.error("Failed to persist audit log: {}", e.getMessage(), e);
        }
    }

    private ApiCallLog mapToEntity(AuditEventDTO event, LocalDateTime timestamp) {
        ApiCallLog entity = new ApiCallLog();

        entity.setBusinessCorrelationId(event.getBusinessCorrelationId());
        entity.setCorrelationId(event.getCorrelationId());
        entity.setReferenceId(event.getReferenceId());

        entity.setUsername(event.getUsername());
        entity.setRole(event.getRole());
        entity.setServiceName(event.getServiceName());

        entity.setUrl(event.getUrl());
        entity.setBoundType(event.getBoundType());

        entity.setStatusCode(event.getResponseStatus());
        entity.setSuccess(event.getResponseStatus() != null
                && event.getResponseStatus() < 400);

        entity.setDuration(event.getDurationMs());

        entity.setRequest(event.getRequestPayload());
        entity.setResponse(event.getResponsePayload());

        entity.setErrorMsg(event.getErrorMessage());

        entity.setLogTime(timestamp);

        return entity;
    }

    public Page<ApiCallLog> getAllLogs(int page, int size) {
        return pgRepo.findAll(
                PageRequest.of(page, size,
                        Sort.by(Sort.Direction.DESC, "logTime")));
    }

    public Page<ApiCallLog> getByUsername(String username, int page, int size) {
        return pgRepo.findByUsernameOrderByLogTimeDesc(
                username,
                PageRequest.of(page, size));
    }

    public Page<ApiCallLog> getByService(String service, int page, int size) {
        return pgRepo.findByServiceNameOrderByLogTimeDesc(
                service,
                PageRequest.of(page, size));
    }

    public Page<ApiCallLog> getErrors(int page, int size) {
        return pgRepo.findBySuccessFalseOrderByLogTimeDesc(
                PageRequest.of(page, size));
    }

    public Page<ApiCallLog> getByDateRange(
            Instant from,
            Instant to,
            int page,
            int size) {

        return pgRepo.findByLogTimeBetweenOrderByLogTimeDesc(
                LocalDateTime.ofInstant(from, java.time.ZoneOffset.UTC),
                LocalDateTime.ofInstant(to, java.time.ZoneOffset.UTC),
                PageRequest.of(page, size));
    }

    public Page<ApiCallLog> getByRole(String role, int page, int size) {
        return pgRepo.findByRoleOrderByLogTimeDesc(
                role, PageRequest.of(page, size));
    }

    public Page<ApiCallLog> getByBusinessCorrelationId(String businessCorrelationId,
            int page,
            int size) {
        return pgRepo.findByBusinessCorrelationId(businessCorrelationId,
                PageRequest.of(page, size));
    }

    public Page<ApiCallLog> getByBoundType(String boundT,
            int page,
            int size) {
        return pgRepo.findByBoundType(boundT,
                PageRequest.of(page, size));
    }
}
