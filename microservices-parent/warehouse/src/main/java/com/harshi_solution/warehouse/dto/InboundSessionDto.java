package com.harshi_solution.warehouse.dto;

import java.time.LocalDateTime;

public record InboundSessionDto(
        Long id, Long wareId, String truckRef,
        String status, String createdBy,
        LocalDateTime createdAt, LocalDateTime completedAt,
        int totalTotes, int completedTotes) {
}
