package com.harshi_solution.warehouse.dto;

import jakarta.validation.constraints.NotBlank;

public record ScanLpnRequest(
    @NotBlank String lpn
) {}
