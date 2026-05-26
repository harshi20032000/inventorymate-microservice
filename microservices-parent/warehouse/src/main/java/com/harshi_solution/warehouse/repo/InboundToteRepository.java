package com.harshi_solution.warehouse.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harshi_solution.warehouse.entities.InboundTote;
import com.harshi_solution.warehouse.entities.ToteStatus;

@Repository
public interface InboundToteRepository
    extends JpaRepository<InboundTote, Long> {

    List<InboundTote> findBySession_Id(Long sessionId);
    Optional<InboundTote> findBySession_IdAndLpn(Long sessionId, String lpn);
    long countBySession_IdAndStatus(Long sessionId, ToteStatus status);
}
