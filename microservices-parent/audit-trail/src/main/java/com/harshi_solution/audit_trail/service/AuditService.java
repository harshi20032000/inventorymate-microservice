package com.harshi_solution.audit_trail.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Optional;

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
import com.harshi_solution.audit_trail.postgres.entity.AuditSummary;
import com.harshi_solution.audit_trail.postgres.repository.AuditSummaryRepository;

@Service
public class AuditService {

    private static final Logger log = LoggerFactory.getLogger(AuditService.class);

    @Autowired(required = false)
    private AuditLogMongoRepository mongoRepo;
    private final AuditSummaryRepository pgRepo;

    public AuditService(
            AuditSummaryRepository pgRepo) {
        this.pgRepo = pgRepo;
    }

    private boolean isMongoEnabled() {
        return mongoRepo != null;
    }
    
     @Transactional
    public void record(AuditEventDTO event) {
        Instant timestamp = event.getTimestamp() != null
            ? event.getTimestamp() : Instant.now();

        // write to MongoDB only if enabled
        if (isMongoEnabled()) {
            AuditLogDocument doc = new AuditLogDocument();
            doc.setUsername(event.getUsername());
            doc.setRole(event.getRole());
            doc.setServiceName(event.getServiceName());
            doc.setMethod(event.getMethod());
            doc.setEndpoint(event.getEndpoint());
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
                event.getUsername(), event.getEndpoint());
        }

        // always write summary to PostgreSQL
        LocalDate today = timestamp.atZone(ZoneOffset.UTC).toLocalDate();
        try {
            upsertSummary(event, today);
        } catch (Exception e) {
            log.error("PostgreSQL summary write failed: {}", e.getMessage());
        }
    }

    private void upsertSummary(AuditEventDTO event, LocalDate date) {
        Optional<AuditSummary> existing = pgRepo
                .findByUsernameAndServiceNameAndSummaryDate(
                        event.getUsername(), event.getServiceName(), date);

        AuditSummary summary = existing.orElseGet(() -> {
            AuditSummary s = new AuditSummary();
            s.setUsername(event.getUsername());
            s.setRole(event.getRole());
            s.setServiceName(event.getServiceName());
            s.setSummaryDate(date);
            return s;
        });

        summary.setTotalCalls(summary.getTotalCalls() + 1);
        if (event.getResponseStatus() != null && event.getResponseStatus() >= 400) {
            summary.setErrorCalls(summary.getErrorCalls() + 1);
        }

        // rolling average duration
        if (event.getDurationMs() != null) {
            long prev = summary.getAvgDurationMs() != null ? summary.getAvgDurationMs() : 0L;
            long n = summary.getTotalCalls();
            summary.setAvgDurationMs((prev * (n - 1) + event.getDurationMs()) / n);
        }

        pgRepo.save(summary);
    }

    // ── Read path — MongoDB (detailed) ────────────────────
    public Page<AuditLogDocument> getAllLogs(int page, int size) {
        return mongoRepo.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp")));
    }

    public Page<AuditLogDocument> getByUsername(String username, int page, int size) {
        return mongoRepo.findByUsernameOrderByTimestampDesc(
                username, PageRequest.of(page, size));
    }

    public Page<AuditLogDocument> getByService(String service, int page, int size) {
        return mongoRepo.findByServiceNameOrderByTimestampDesc(
                service, PageRequest.of(page, size));
    }

    public Page<AuditLogDocument> getByRole(String role, int page, int size) {
        return mongoRepo.findByRoleOrderByTimestampDesc(
                role, PageRequest.of(page, size));
    }

    public Page<AuditLogDocument> getByDateRange(
            Instant from, Instant to, int page, int size) {
        return mongoRepo.findByTimestampBetweenOrderByTimestampDesc(
                from, to, PageRequest.of(page, size));
    }

    public Page<AuditLogDocument> getErrors(int page, int size) {
        return mongoRepo.findByIsErrorTrueOrderByTimestampDesc(
                PageRequest.of(page, size));
    }

    // ── Read path — PostgreSQL (summaries) ────────────────
    public java.util.List<AuditSummary> getSummaryByUser(String username) {
        return pgRepo.findByUsernameOrderBySummaryDateDesc(username);
    }

    public java.util.List<Object[]> getUserActivity(LocalDate from, LocalDate to) {
        return pgRepo.findUserActivitySummary(from, to);
    }

    public java.util.List<Object[]> getServiceActivity(LocalDate date) {
        return pgRepo.findServiceActivityForDate(date);
    }
}
