package com.harshi_solution.warehouse.dto;

public record BinLocationDto(
    Long id, Long wareId,
    String aisle, String rack, String slot,
    String binCode, boolean occupied) {}
