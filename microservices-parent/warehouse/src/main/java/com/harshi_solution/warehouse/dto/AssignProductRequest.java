package com.harshi_solution.warehouse.dto;

import jakarta.validation.constraints.NotNull;

public record AssignProductRequest(
    @NotNull Long productId,
    int quantity
) {}
