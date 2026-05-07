package com.harshi_solution.order.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harshi_solution.auth.exception.NotFoundException;
import com.harshi_solution.order.dto.DeliveryNoteResponseDTO;
import com.harshi_solution.order.dto.DispatchRequestDTO;
import com.harshi_solution.order.entities.DeliveryNote;
import com.harshi_solution.order.entities.DeliveryNote.Status;
import com.harshi_solution.order.entities.Order;
import com.harshi_solution.order.entities.OrderLineItem;
import com.harshi_solution.order.repo.DeliveryNoteRepository;
import com.harshi_solution.order.repo.OrderRepository;
import com.harshi_solution.order.util.OrderStatus;

@Service
public class DeliveryNoteService {

    private final DeliveryNoteRepository deliveryNoteRepo;
    private final OrderRepository        orderRepo;

    public DeliveryNoteService(DeliveryNoteRepository deliveryNoteRepo,
                               OrderRepository        orderRepo) {
        this.deliveryNoteRepo = deliveryNoteRepo;
        this.orderRepo        = orderRepo;
    }

    // ── Called automatically when order becomes FULLY_PAID ──
    @Transactional
    public List<DeliveryNote> createDeliveryNotesForOrder(Order order) {
        // idempotent — don't create duplicates if called again
        List<DeliveryNote> existing = deliveryNoteRepo.findByOrderId(order.getOrderId());
        if (!existing.isEmpty()) return existing;

        List<DeliveryNote> notes = new ArrayList<>();

        for (OrderLineItem item : order.getOrderLineItems()) {
            Map<Long, Integer> warehouseQty = item.getWarehouseQuantities();
            if (warehouseQty == null) continue;

            for (Map.Entry<Long, Integer> entry : warehouseQty.entrySet()) {
                Long    wareId   = entry.getKey();
                Integer quantity = entry.getValue();

                if (quantity <= 0) continue;

                DeliveryNote note = new DeliveryNote();
                note.setOrderId(order.getOrderId());
                note.setWareId(wareId);
                note.setProductId(item.getProductId());
                note.setQuantity(quantity);
                note.setStatus(Status.PENDING);

                notes.add(deliveryNoteRepo.save(note));
            }
        }

        return notes;
    }

    // ── GET all notes for an order ──────────────────────────
    public List<DeliveryNoteResponseDTO> getByOrderId(Long orderId) {
        return deliveryNoteRepo.findByOrderId(orderId)
            .stream().map(this::toDTO).collect(Collectors.toList());
    }

    // ── GET all notes for a warehouse ───────────────────────
    public List<DeliveryNoteResponseDTO> getByWareId(Long wareId) {
        return deliveryNoteRepo.findByWareId(wareId)
            .stream().map(this::toDTO).collect(Collectors.toList());
    }

    // ── GET pending notes for a warehouse ───────────────────
    public List<DeliveryNoteResponseDTO> getPendingByWareId(Long wareId) {
        return deliveryNoteRepo.findByWareIdAndStatus(wareId, Status.PENDING)
            .stream().map(this::toDTO).collect(Collectors.toList());
    }

    // ── DISPATCH — warehouse marks their allocation sent ────
    @Transactional
    public DeliveryNoteResponseDTO dispatch(Long noteId, DispatchRequestDTO req) {
        DeliveryNote note = deliveryNoteRepo.findById(noteId)
            .orElseThrow(() -> new NotFoundException("DeliveryNote", noteId));

        if (note.getStatus() != Status.PENDING) {
            throw new IllegalStateException(
                "Delivery note is already " + note.getStatus());
        }

        note.setStatus(Status.DISPATCHED);
        note.setDispatchedAt(LocalDateTime.now());
        note.setDispatchedBy(getCurrentUsername());
        if (req != null) note.setNotes(req.getNotes());

        deliveryNoteRepo.save(note);

        // update order status based on all notes
        updateOrderDispatchStatus(note.getOrderId());

        return toDTO(note);
    }

    // ── DELIVER — confirm goods received ────────────────────
    @Transactional
    public DeliveryNoteResponseDTO deliver(Long noteId, DispatchRequestDTO req) {
        DeliveryNote note = deliveryNoteRepo.findById(noteId)
            .orElseThrow(() -> new NotFoundException("DeliveryNote", noteId));

        if (note.getStatus() != Status.DISPATCHED) {
            throw new IllegalStateException(
                "Can only deliver a DISPATCHED note, current: " + note.getStatus());
        }

        note.setStatus(Status.DELIVERED);
        note.setDeliveredAt(LocalDateTime.now());
        note.setDeliveredBy(getCurrentUsername());
        if (req != null && req.getNotes() != null) note.setNotes(req.getNotes());

        deliveryNoteRepo.save(note);
        updateOrderDispatchStatus(note.getOrderId());

        return toDTO(note);
    }

    // ── Update order status based on delivery note states ───
    private void updateOrderDispatchStatus(Long orderId) {
        Order order = orderRepo.findById(orderId)
            .orElseThrow(() -> new NotFoundException("Order", orderId));

        List<DeliveryNote> allNotes = deliveryNoteRepo.findByOrderId(orderId);
        if (allNotes.isEmpty()) return;

        long pendingCount    = allNotes.stream().filter(n -> n.getStatus() == Status.PENDING).count();
        long deliveredCount  = allNotes.stream().filter(n -> n.getStatus() == Status.DELIVERED).count();
        long total           = allNotes.size();

        if (deliveredCount == total) {
            order.setCurrentStatus(OrderStatus.FULLY_DISPATCHED);
        } else if (pendingCount == total) {
            order.setCurrentStatus(OrderStatus.FULLY_PAID);   // nothing dispatched yet
        } else {
            order.setCurrentStatus(OrderStatus.PARTIALLY_DISPATCHED);
        }

        orderRepo.save(order);
    }

    // ── Mapper ───────────────────────────────────────────────
    private DeliveryNoteResponseDTO toDTO(DeliveryNote n) {
        DeliveryNoteResponseDTO dto = new DeliveryNoteResponseDTO();
        dto.setId(n.getId());
        dto.setOrderId(n.getOrderId());
        dto.setWareId(n.getWareId());
        dto.setProductId(n.getProductId());
        dto.setQuantity(n.getQuantity());
        dto.setStatus(n.getStatus());
        dto.setDispatchedAt(n.getDispatchedAt());
        dto.setDispatchedBy(n.getDispatchedBy());
        dto.setDeliveredAt(n.getDeliveredAt());
        dto.setDeliveredBy(n.getDeliveredBy());
        dto.setNotes(n.getNotes());
        dto.setCreatedAt(n.getCreatedAt());
        return dto;
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getName() : "system";
    }
}
