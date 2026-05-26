package com.harshi_solution.warehouse.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "inbound_sessions")
public class InboundSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ware_id", nullable = false)
    private Long wareId;

    @Column(name = "truck_ref", nullable = false)
    private String truckRef; // truck number / reference

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InboundStatus status = InboundStatus.OPEN;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWareId() {
        return wareId;
    }

    public void setWareId(Long wareId) {
        this.wareId = wareId;
    }

    public String getTruckRef() {
        return truckRef;
    }

    public void setTruckRef(String truckRef) {
        this.truckRef = truckRef;
    }

    public InboundStatus getStatus() {
        return status;
    }

    public void setStatus(InboundStatus status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    @PrePersist
    private void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    protected InboundSession() {
    }

    // package-private constructor
   public InboundSession(
            Long wareId,
            String truckRef,
            InboundStatus status,
            String createdBy,
            LocalDateTime createdAt,
            LocalDateTime completedAt) {
        this.wareId = wareId;
        this.truckRef = truckRef;
        this.status = status;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
    }

}
