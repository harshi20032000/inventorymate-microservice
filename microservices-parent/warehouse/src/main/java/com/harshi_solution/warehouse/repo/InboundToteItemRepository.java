package com.harshi_solution.warehouse.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harshi_solution.warehouse.entities.InboundToteItem;
import com.harshi_solution.warehouse.entities.ItemStatus;

@Repository
public interface InboundToteItemRepository
    extends JpaRepository<InboundToteItem, Long> {

    List<InboundToteItem> findByTote_Id(Long toteId);
    long countByTote_IdAndStatus(Long toteId, ItemStatus status);
}
