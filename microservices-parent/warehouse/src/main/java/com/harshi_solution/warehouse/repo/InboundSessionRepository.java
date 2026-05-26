package com.harshi_solution.warehouse.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harshi_solution.warehouse.entities.InboundSession;
import com.harshi_solution.warehouse.entities.InboundStatus;

@Repository
public interface InboundSessionRepository
        extends JpaRepository<InboundSession, Long> {

    List<InboundSession> findByWareIdOrderByCreatedAtDesc(Long wareId);

    List<InboundSession> findByWareIdAndStatusOrderByCreatedAtDesc(
            Long wareId, InboundStatus status);
}
