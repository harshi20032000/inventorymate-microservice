package com.harshi_solution.user.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harshi_solution.user.entities.Role;
import com.harshi_solution.user.entities.UserRegisterEntity;
@Repository
public interface UserRegisterEntityRepository extends JpaRepository<UserRegisterEntity, Long> {

    Optional<UserRegisterEntity> findByUsername(String username);

    boolean existsByUsername(String username);

    List<UserRegisterEntity> findByRole(Role role);
}
