package com.harshi_solution.warehouse.entities;

public enum ToteStatus {
    PENDING, // tote scanned, no items processed yet
    SCANNING, // items being scanned and placed
    COMPLETED // all items placed
}
