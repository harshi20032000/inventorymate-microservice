package com.harshi_solution.user.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harshi_solution.user.entities.Reps;

@Repository
public interface RepsRepository extends JpaRepository<Reps, Long>{

    Optional<Reps> findById(Long id);

}
