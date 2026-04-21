package com.harshi_solution.payment.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshi_solution.payment.entities.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
