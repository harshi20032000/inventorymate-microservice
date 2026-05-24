package com.harshi_solution.warehouse.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harshi_solution.warehouse.entities.ProductBinAssignment;

@Repository
public interface ProductBinAssignmentRepository
    extends JpaRepository<ProductBinAssignment, Long> {

    List<ProductBinAssignment> findByBin_WareId(Long wareId);
    List<ProductBinAssignment> findByProductId(Long productId);
    Optional<ProductBinAssignment> findByProductIdAndBin_Id(Long productId, Long binId);
    List<ProductBinAssignment> findByBin_Id(Long binId);
}
