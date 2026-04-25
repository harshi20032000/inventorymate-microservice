package com.harshi_solution.audit_trail.postgres.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.harshi_solution.audit_trail.postgres.entity.AuditSummary;

public interface AuditSummaryRepository
        extends JpaRepository<AuditSummary, Long> {

    Optional<AuditSummary> findByUsernameAndServiceNameAndSummaryDate(
            String username, String serviceName, LocalDate date);

    List<AuditSummary> findByUsernameOrderBySummaryDateDesc(String username);

    List<AuditSummary> findBySummaryDateBetweenOrderBySummaryDateDesc(
            LocalDate from, LocalDate to);

    // total calls per user across all services for a date range
    @Query("""
                SELECT s.username, SUM(s.totalCalls) as total, SUM(s.errorCalls) as errors
                FROM AuditSummary s
                WHERE s.summaryDate BETWEEN :from AND :to
                GROUP BY s.username
                ORDER BY total DESC
            """)
    List<Object[]> findUserActivitySummary(LocalDate from, LocalDate to);

    // most active services
    @Query("""
                SELECT s.serviceName, SUM(s.totalCalls) as total
                FROM AuditSummary s
                WHERE s.summaryDate = :date
                GROUP BY s.serviceName
                ORDER BY total DESC
            """)
    List<Object[]> findServiceActivityForDate(LocalDate date);
}
