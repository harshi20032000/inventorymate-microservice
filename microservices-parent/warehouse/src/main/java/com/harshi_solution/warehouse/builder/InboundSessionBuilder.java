package com.harshi_solution.warehouse.builder;

import java.time.LocalDateTime;
import java.util.Objects;

import com.harshi_solution.warehouse.entities.InboundSession;
import com.harshi_solution.warehouse.entities.InboundStatus;

public class InboundSessionBuilder {

    private Long wareId;
    private String truckRef;
    private InboundStatus status = InboundStatus.OPEN;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    public InboundSessionBuilder wareId(Long wareId) {
        this.wareId = wareId;
        return this;
    }

    public InboundSessionBuilder truckRef(String truckRef) {
        this.truckRef = truckRef;
        return this;
    }

    public InboundSessionBuilder status(InboundStatus status) {
        this.status = status;
        return this;
    }

    public InboundSessionBuilder createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public InboundSessionBuilder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public InboundSessionBuilder completedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
        return this;
    }

    public InboundSession build() {

        Objects.requireNonNull(wareId, "wareId is required");
        Objects.requireNonNull(truckRef, "truckRef is required");
        Objects.requireNonNull(createdBy, "createdBy is required");

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }

        return new InboundSession(
                wareId,
                truckRef,
                status,
                createdBy,
                createdAt,
                completedAt);
    }
}
