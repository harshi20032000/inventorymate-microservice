package com.harshi_solution.order.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harshi_solution.auth.dto.BaseUIResponse;
import com.harshi_solution.auth.util.ResponseBuilder;
import com.harshi_solution.order.dto.DeliveryNoteResponseDTO;
import com.harshi_solution.order.dto.DispatchRequestDTO;
import com.harshi_solution.order.service.DeliveryNoteService;

@RestController
@RequestMapping("/api/v1")
public class DeliveryNoteController {

    private final DeliveryNoteService service;

    public DeliveryNoteController(DeliveryNoteService service) {
        this.service = service;
    }

    // GET /api/v1/orders/{orderId}/delivery-notes
    @GetMapping("/orders/{orderId}/delivery-notes")
    public BaseUIResponse<List<DeliveryNoteResponseDTO>> getByOrder(
            @PathVariable Long orderId) {
        return ResponseBuilder.success("Delivery notes fetched",
            service.getByOrderId(orderId));
    }

    // GET /api/v1/warehouses/{wareId}/delivery-notes
    @GetMapping("/warehouses/{wareId}/delivery-notes")
    public BaseUIResponse<List<DeliveryNoteResponseDTO>> getByWarehouse(
            @PathVariable Long wareId) {
        return ResponseBuilder.success("Delivery notes fetched",
            service.getByWareId(wareId));
    }

    // GET /api/v1/warehouses/{wareId}/delivery-notes/pending
    @GetMapping("/warehouses/{wareId}/delivery-notes/pending")
    public BaseUIResponse<List<DeliveryNoteResponseDTO>> getPendingByWarehouse(
            @PathVariable Long wareId) {
        return ResponseBuilder.success("Pending delivery notes fetched",
            service.getPendingByWareId(wareId));
    }

    // POST /api/v1/delivery-notes/{noteId}/dispatch
    @PostMapping("/delivery-notes/{noteId}/dispatch")
    public BaseUIResponse<DeliveryNoteResponseDTO> dispatch(
            @PathVariable Long noteId,
            @RequestBody(required = false) DispatchRequestDTO req) {
        return ResponseBuilder.success("Delivery note dispatched",
            service.dispatch(noteId, req));
    }

    // POST /api/v1/delivery-notes/{noteId}/deliver
    @PostMapping("/delivery-notes/{noteId}/deliver")
    public BaseUIResponse<DeliveryNoteResponseDTO> deliver(
            @PathVariable Long noteId,
            @RequestBody(required = false) DispatchRequestDTO req) {
        return ResponseBuilder.success("Delivery note delivered",
            service.deliver(noteId, req));
    }
}
