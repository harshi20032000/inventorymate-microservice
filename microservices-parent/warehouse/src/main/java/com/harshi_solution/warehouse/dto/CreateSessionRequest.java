package com.harshi_solution.warehouse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateSessionRequest(
    @NotNull  Long   wareId,
    @NotBlank String truckRef
) {}
