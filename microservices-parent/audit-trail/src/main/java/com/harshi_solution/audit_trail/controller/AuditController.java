package com.harshi_solution.audit_trail.controller;

import java.time.Instant;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harshi_solution.audit_trail.dto.AuditEventDTO;
import com.harshi_solution.audit_trail.postgres.entity.ApiCallLog;
import com.harshi_solution.audit_trail.service.AuditService;
import com.harshi_solution.auth.dto.BaseUIResponse;
import com.harshi_solution.auth.util.ResponseBuilder;

@RestController
@RequestMapping("/api/v1/audit")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    // ── Write endpoint — called by other services ─────────
    @PostMapping("/record")
    public BaseUIResponse<String> record(@RequestBody AuditEventDTO event) {
        auditService.record(event);
        return ResponseBuilder.success("Recorded", "OK");
    }

    // ── Admin read endpoints — all logs (MongoDB) ─────────
    @GetMapping("/logs")
    public BaseUIResponse<Page<ApiCallLog>> getAllLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseBuilder.success("Logs fetched",
                auditService.getAllLogs(page, size));
    }

    @GetMapping("/logs/user/{username}")
    public BaseUIResponse<Page<ApiCallLog>> getByUser(
            @PathVariable String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseBuilder.success("Logs fetched",
                auditService.getByUsername(username, page, size));
    }

    @GetMapping("/logs/service/{service}")
    public BaseUIResponse<Page<ApiCallLog>> getByService(
            @PathVariable String service,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseBuilder.success("Logs fetched",
                auditService.getByService(service, page, size));
    }

    @GetMapping("/logs/role/{role}")
    public BaseUIResponse<Page<ApiCallLog>> getByRole(
            @PathVariable String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseBuilder.success("Logs fetched",
                auditService.getByRole(role, page, size));
    }

    @GetMapping("/logs/errors")
    public BaseUIResponse<Page<ApiCallLog>> getErrors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseBuilder.success("Error logs fetched",
                auditService.getErrors(page, size));
    }

    @GetMapping("/logs/range")
    public BaseUIResponse<Page<ApiCallLog>> getByRange(
            @RequestParam Instant from,
            @RequestParam Instant to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseBuilder.success("Logs fetched",
                auditService.getByDateRange(from, to, page, size));
    }

    @GetMapping("/logs/businessCorrelationId/{businessCorrelationId}")
    public BaseUIResponse<Page<ApiCallLog>> getByBusinessCorrelationId(
            @PathVariable String businessCorrelationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseBuilder.success("Logs fetched",
                auditService.getByBusinessCorrelationId(businessCorrelationId, page, size));
    }

    @GetMapping("/logs/bound/{boundType}")
    public BaseUIResponse<Page<ApiCallLog>> getByBoundType(
            @PathVariable String boundType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseBuilder.success("Logs fetched",
                auditService.getByBoundType(boundType, page, size));
    }
}
