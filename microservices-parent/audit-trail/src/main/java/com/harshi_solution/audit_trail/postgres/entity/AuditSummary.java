package com.harshi_solution.audit_trail.postgres.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "audit_summary", uniqueConstraints = @UniqueConstraint(columnNames = { "username", "service_name",
        "summary_date" }), indexes = {
                @Index(name = "idx_summary_username", columnList = "username"),
                @Index(name = "idx_summary_date", columnList = "summary_date"),
                @Index(name = "idx_summary_service", columnList = "service_name"),
        })
public class AuditSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "role")
    private String role;

    @Column(name = "service_name", nullable = false)
    private String serviceName;

    @Column(name = "summary_date", nullable = false)
    private LocalDate summaryDate;

    @Column(name = "total_calls", nullable = false)
    private long totalCalls = 0L;

    @Column(name = "error_calls", nullable = false)
    private long errorCalls = 0L;

    @Column(name = "avg_duration_ms")
    private Long avgDurationMs;

    // ── getters / setters ────────────────────────────────
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String u) {
        this.username = u;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String r) {
        this.role = r;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String s) {
        this.serviceName = s;
    }

    public LocalDate getSummaryDate() {
        return summaryDate;
    }

    public void setSummaryDate(LocalDate d) {
        this.summaryDate = d;
    }

    public long getTotalCalls() {
        return totalCalls;
    }

    public void setTotalCalls(long t) {
        this.totalCalls = t;
    }

    public long getErrorCalls() {
        return errorCalls;
    }

    public void setErrorCalls(long e) {
        this.errorCalls = e;
    }

    public Long getAvgDurationMs() {
        return avgDurationMs;
    }

    public void setAvgDurationMs(Long a) {
        this.avgDurationMs = a;
    }
}
