package com.harshi_solution.warehouse.dto;

import java.time.LocalDateTime;

public record InboundToteItemDto(
        Long id, Long toteId,
        Long productId, String productName,
        int receivedQuantity,
        Long binId, String binCode,
        String status,
        LocalDateTime placedAt, String placedBy) {
}
