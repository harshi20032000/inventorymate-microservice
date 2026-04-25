package com.harshi_solution.audit_trail.controller;


import java.time.Instant;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harshi_solution.audit_trail.dto.AuditEventDTO;
import com.harshi_solution.audit_trail.mongo.document.AuditLogDocument;
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
    // POST /api/v1/audit/record
    @PostMapping("/record")
    public BaseUIResponse<String> record(@RequestBody AuditEventDTO event) {
        auditService.record(event);
        return ResponseBuilder.success("Recorded", "OK");
    }

    // ── Admin read endpoints — all logs (MongoDB) ─────────

    // GET /api/v1/audit/logs?page=0&size=50
    @GetMapping("/logs")
    public BaseUIResponse<Page<AuditLogDocument>> getAllLogs(
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseBuilder.success("Logs fetched",
            auditService.getAllLogs(page, size));
    }

    // GET /api/v1/audit/logs/user/harshi123
    @GetMapping("/logs/user/{username}")
    public BaseUIResponse<Page<AuditLogDocument>> getByUser(
            @PathVariable String username,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseBuilder.success("Logs fetched",
            auditService.getByUsername(username, page, size));
    }

    // GET /api/v1/audit/logs/service/order
    @GetMapping("/logs/service/{service}")
    public BaseUIResponse<Page<AuditLogDocument>> getByService(
            @PathVariable String service,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseBuilder.success("Logs fetched",
            auditService.getByService(service, page, size));
    }

    // GET /api/v1/audit/logs/role/ROLE_REPS
    @GetMapping("/logs/role/{role}")
    public BaseUIResponse<Page<AuditLogDocument>> getByRole(
            @PathVariable String role,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseBuilder.success("Logs fetched",
            auditService.getByRole(role, page, size));
    }

    // GET /api/v1/audit/logs/errors
    @GetMapping("/logs/errors")
    public BaseUIResponse<Page<AuditLogDocument>> getErrors(
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseBuilder.success("Error logs fetched",
            auditService.getErrors(page, size));
    }

    // GET /api/v1/audit/logs/range?from=2026-01-01T00:00:00Z&to=2026-04-30T23:59:59Z
    @GetMapping("/logs/range")
    public BaseUIResponse<Page<AuditLogDocument>> getByRange(
            @RequestParam Instant from,
            @RequestParam Instant to,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseBuilder.success("Logs fetched",
            auditService.getByDateRange(from, to, page, size));
    }

    // ── Summary endpoints (PostgreSQL) ────────────────────

    // GET /api/v1/audit/summary/user/harshi123
    @GetMapping("/summary/user/{username}")
    public BaseUIResponse<?> getSummaryByUser(@PathVariable String username) {
        return ResponseBuilder.success("Summary fetched",
            auditService.getSummaryByUser(username));
    }

    // GET /api/v1/audit/summary/activity?from=2026-04-01&to=2026-04-30
    @GetMapping("/summary/activity")
    public BaseUIResponse<?> getUserActivity(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseBuilder.success("Activity fetched",
            auditService.getUserActivity(from, to));
    }

    // GET /api/v1/audit/summary/services?date=2026-04-25
    @GetMapping("/summary/services")
    public BaseUIResponse<?> getServiceActivity(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseBuilder.success("Service activity fetched",
            auditService.getServiceActivity(date));
    }
}
