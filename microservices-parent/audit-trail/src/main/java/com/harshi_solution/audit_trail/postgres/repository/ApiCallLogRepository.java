package com.harshi_solution.audit_trail.postgres.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harshi_solution.audit_trail.postgres.entity.ApiCallLog;
@Repository
public interface ApiCallLogRepository
                extends JpaRepository<ApiCallLog, Long> {

        Page<ApiCallLog> findByUsernameOrderByLogTimeDesc(
                        String username,
                        Pageable pageable);

        Page<ApiCallLog> findByServiceNameOrderByLogTimeDesc(
                        String serviceName,
                        Pageable pageable);

        Page<ApiCallLog> findBySuccessFalseOrderByLogTimeDesc(
                        Pageable pageable);

        Page<ApiCallLog> findByLogTimeBetweenOrderByLogTimeDesc(
                        LocalDateTime from,
                        LocalDateTime to,
                        Pageable pageable);
}
