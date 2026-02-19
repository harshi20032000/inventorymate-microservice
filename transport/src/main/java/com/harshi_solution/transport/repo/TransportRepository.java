package com.harshi_solution.transport.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshi_solution.transport.entities.Transport;

public interface TransportRepository extends JpaRepository<Transport, Long> {
    Transport findByTransportId(Long transportId);
}
