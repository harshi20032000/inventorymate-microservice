package com.harshi_solution.warehouse.dto;

import jakarta.validation.constraints.NotNull;

public record ScanSkuRequest(
    @NotNull Long   productId,
    @NotNull Long   binId,
    int             quantity   // how many of this SKU in this tote
) {}
