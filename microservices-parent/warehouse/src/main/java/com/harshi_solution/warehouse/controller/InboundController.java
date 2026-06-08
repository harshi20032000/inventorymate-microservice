package com.harshi_solution.warehouse.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harshi_solution.auth.dto.BaseUIResponse;
import com.harshi_solution.auth.util.ResponseBuilder;
import com.harshi_solution.warehouse.dto.CreateSessionRequest;
import com.harshi_solution.warehouse.dto.InboundSessionDto;
import com.harshi_solution.warehouse.dto.InboundToteDto;
import com.harshi_solution.warehouse.dto.InboundToteItemDto;
import com.harshi_solution.warehouse.dto.PlaceItemRequest;
import com.harshi_solution.warehouse.dto.ScanLpnRequest;
import com.harshi_solution.warehouse.dto.ScanSkuRequest;
import com.harshi_solution.warehouse.service.InboundService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/inbound")
public class InboundController {

    private final InboundService inboundService;

    InboundController(InboundService inboundService) {
        this.inboundService = inboundService;
    }

    // ── Sessions ──────────────────────────────────────────

    @PostMapping("/sessions")
    public BaseUIResponse<InboundSessionDto> createSession(
            @Valid @RequestBody CreateSessionRequest req) {
        return ResponseBuilder.success("Session created successfully", inboundService.createSession(req));

    }

    @GetMapping("/sessions/warehouse/{wareId}")
    public BaseUIResponse<List<InboundSessionDto>> getSessions(
            @PathVariable Long wareId) {
        return ResponseBuilder.success("sessions fetched", inboundService.getSessions(wareId));
    }

    @GetMapping("/sessions/warehouse/{wareId}/active")
    public BaseUIResponse<List<InboundSessionDto>> getActiveSessions(
            @PathVariable Long wareId) {
        return ResponseBuilder.success("active sessions fetched", inboundService.getActiveSessions(wareId));
    }

    @GetMapping("/sessions/{sessionId}")
    public BaseUIResponse<InboundSessionDto> getSession(
            @PathVariable Long sessionId) {
        return ResponseBuilder.success("session fetched by id", inboundService.getSession(sessionId));
    }

    @PostMapping("/sessions/{sessionId}/complete")
    public BaseUIResponse<InboundSessionDto> completeSession(
            @PathVariable Long sessionId) {
        return ResponseBuilder.success("session completed", inboundService.completeSession(sessionId));
    }

    // ── Totes ─────────────────────────────────────────────

    @PostMapping("/sessions/{sessionId}/scan-lpn")
    public BaseUIResponse<InboundToteDto> scanLpn(
            @PathVariable Long sessionId,
            @Valid @RequestBody ScanLpnRequest req) {
        return ResponseBuilder.success("lpn scanned", inboundService.scanLpn(sessionId, req));
    }

    @GetMapping("/sessions/{sessionId}/totes")
    public BaseUIResponse<List<InboundToteDto>> getTotes(
            @PathVariable Long sessionId) {
        return ResponseBuilder.success("tores fetched for session id", inboundService.getTotes(sessionId));
    }

    // ── Items ─────────────────────────────────────────────

    @PostMapping("/totes/{toteId}/scan-sku")
    public BaseUIResponse<InboundToteItemDto> scanSku(
            @PathVariable Long toteId,
            @Valid @RequestBody ScanSkuRequest req) {
        return ResponseBuilder.success(" ", inboundService.scanSku(toteId, req));
    }

    @GetMapping("/totes/{toteId}/items")
    public BaseUIResponse<List<InboundToteItemDto>> getToteItems(
            @PathVariable Long toteId) {
        return ResponseBuilder.success("ToteItems fetched", inboundService.getToteItems(toteId));
    }

    @PostMapping("/items/{itemId}/place")
    public BaseUIResponse<InboundToteItemDto> placeItem(
            @PathVariable Long itemId,
            @Valid @RequestBody PlaceItemRequest req) {
        return ResponseBuilder.success("Item placed for itemId", inboundService.placeItem(itemId, req));
    }
}
