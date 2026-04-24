package com.harshi_solution.warehouse.exception;

import com.harshi_solution.auth.exception.NotFoundException;

public class WarehouseNotFoundException extends NotFoundException {
    public WarehouseNotFoundException(Long id) {
        super("Warehouse", id);
    }
}
