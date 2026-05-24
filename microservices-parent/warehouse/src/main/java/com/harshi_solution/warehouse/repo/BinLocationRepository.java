package com.harshi_solution.warehouse.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harshi_solution.warehouse.entities.BinLocation;

@Repository
public interface BinLocationRepository extends JpaRepository<BinLocation, Long> {
    List<BinLocation> findByWareId(Long wareId);

    Optional<BinLocation> findByWareIdAndBinCode(Long wareId, String binCode);

    List<BinLocation> findByWareIdAndOccupied(Long wareId, boolean occupied);
}
