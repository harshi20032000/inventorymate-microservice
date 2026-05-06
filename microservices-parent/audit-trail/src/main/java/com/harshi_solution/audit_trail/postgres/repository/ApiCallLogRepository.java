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

        Page<ApiCallLog> findByRoleOrderByTimestampDesc(
                        String role,
                        Pageable pageable);

        Page<ApiCallLog> findByCorrelationId(String correlationId, Pageable pageable);

        Page<ApiCallLog> findByBusinessCorrelationId(String id, Pageable pageable);

        Page<ApiCallLog> findByBoundType(String boundType, Pageable pageable);

        Page<ApiCallLog> findByRole(String role, Pageable pageable);

        Page<ApiCallLog> findByServiceName(String serviceName, Pageable pageable);

        Page<ApiCallLog> findByUsername(String username, Pageable pageable);

        Page<ApiCallLog> findBySuccessFalse(Pageable pageable);

        Page<ApiCallLog> findByLogTimeBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);
}
