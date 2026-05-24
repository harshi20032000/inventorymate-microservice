package com.harshi_solution.warehouse.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateBinRequest(
    @NotBlank String aisle,
    @NotBlank String rack,
    @NotBlank String slot,
    int capacity
) {}
