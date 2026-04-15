package com.harshi_solution.warehouse.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harshi_solution.warehouse.entities.Warehouse;

@Repository
public interface WarehouseRepo extends JpaRepository<Warehouse, Long>{

}
