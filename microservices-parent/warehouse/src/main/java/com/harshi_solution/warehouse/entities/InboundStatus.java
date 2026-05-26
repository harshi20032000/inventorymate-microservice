package com.harshi_solution.warehouse.entities;

public enum InboundStatus {
    OPEN,           // session created, no totes scanned yet
    IN_PROGRESS,    // at least one tote being processed
    COMPLETED       // all totes placed
}
