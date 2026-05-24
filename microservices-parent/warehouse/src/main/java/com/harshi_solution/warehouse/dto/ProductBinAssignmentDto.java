package com.harshi_solution.warehouse.dto;

import java.time.LocalDateTime;

public record ProductBinAssignmentDto(
    Long id, Long productId, String productName,
    Long binId, String binCode, int quantity,
    LocalDateTime updatedAt
) {}
