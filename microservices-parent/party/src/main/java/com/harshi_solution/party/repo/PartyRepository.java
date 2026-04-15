package com.harshi_solution.party.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshi_solution.party.entities.Party;

public interface PartyRepository extends JpaRepository<Party, Long> {

    Optional<Party> findByPartyName(String partyName);

    boolean existsByPartyName(String partyName);
}