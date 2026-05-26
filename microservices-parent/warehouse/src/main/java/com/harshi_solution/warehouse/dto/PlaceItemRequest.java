package com.harshi_solution.warehouse.dto;

import jakarta.validation.constraints.NotBlank;

public record PlaceItemRequest(
    @NotBlank String placedBy
) {}
