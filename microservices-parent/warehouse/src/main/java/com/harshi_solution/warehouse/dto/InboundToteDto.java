package com.harshi_solution.warehouse.dto;

import java.time.LocalDateTime;

public record InboundToteDto(
        Long id, Long sessionId, String lpn,
        String status, LocalDateTime scannedAt,
        int totalItems, int placedItems) {
}
