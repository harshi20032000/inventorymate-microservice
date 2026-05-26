package com.harshi_solution.warehouse.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "inbound_totes", uniqueConstraints = @UniqueConstraint(columnNames = { "session_id", "lpn" }))
public class InboundTote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private InboundSession session;

    @Column(nullable = false)
    private String lpn; // scanned license plate number

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ToteStatus status = ToteStatus.PENDING;

    @Column(name = "scanned_at")
    private LocalDateTime scannedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @PrePersist
    private void onCreate() {
        this.scannedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InboundSession getSession() {
        return session;
    }

    public void setSession(InboundSession session) {
        this.session = session;
    }

    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn;
    }

    public ToteStatus getStatus() {
        return status;
    }

    public void setStatus(ToteStatus status) {
        this.status = status;
    }

    public LocalDateTime getScannedAt() {
        return scannedAt;
    }

    public void setScannedAt(LocalDateTime scannedAt) {
        this.scannedAt = scannedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    protected InboundTote(){

    }
    public InboundTote(
            InboundSession session,
            String lpn,
            ToteStatus status,
            LocalDateTime scannedAt, LocalDateTime completedAt
    ) {
        this.session = session;
        this.lpn = lpn;
        this.status = status;
        this.scannedAt = scannedAt;
        this.completedAt=completedAt;
    }


}
