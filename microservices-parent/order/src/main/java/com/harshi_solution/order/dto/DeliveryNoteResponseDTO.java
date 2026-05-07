package com.harshi_solution.order.dto;

import java.time.LocalDateTime;

import com.harshi_solution.order.entities.DeliveryNote.Status;

public class DeliveryNoteResponseDTO {

    private Long          id;
    private Long          orderId;
    private Long          wareId;
    private Long          productId;
    private Integer       quantity;
    private Status        status;
    private LocalDateTime dispatchedAt;
    private String        dispatchedBy;
    private LocalDateTime deliveredAt;
    private String        deliveredBy;
    private String        notes;
    private LocalDateTime createdAt;

    // ── getters / setters ────────────────────────────────
    public Long          getId()                         { return id; }
    public void          setId(Long v)                   { this.id = v; }
    public Long          getOrderId()                    { return orderId; }
    public void          setOrderId(Long v)              { this.orderId = v; }
    public Long          getWareId()                     { return wareId; }
    public void          setWareId(Long v)               { this.wareId = v; }
    public Long          getProductId()                  { return productId; }
    public void          setProductId(Long v)            { this.productId = v; }
    public Integer       getQuantity()                   { return quantity; }
    public void          setQuantity(Integer v)          { this.quantity = v; }
    public Status        getStatus()                     { return status; }
    public void          setStatus(Status v)             { this.status = v; }
    public LocalDateTime getDispatchedAt()               { return dispatchedAt; }
    public void          setDispatchedAt(LocalDateTime v){ this.dispatchedAt = v; }
    public String        getDispatchedBy()               { return dispatchedBy; }
    public void          setDispatchedBy(String v)       { this.dispatchedBy = v; }
    public LocalDateTime getDeliveredAt()                { return deliveredAt; }
    public void          setDeliveredAt(LocalDateTime v) { this.deliveredAt = v; }
    public String        getDeliveredBy()                { return deliveredBy; }
    public void          setDeliveredBy(String v)        { this.deliveredBy = v; }
    public String        getNotes()                      { return notes; }
    public void          setNotes(String v)              { this.notes = v; }
    public LocalDateTime getCreatedAt()                  { return createdAt; }
    public void          setCreatedAt(LocalDateTime v)   { this.createdAt = v; }
}
