package com.harshi_solution.order.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(name = "delivery_note",
       indexes = {
           @Index(name = "idx_dn_order",   columnList = "order_id"),
           @Index(name = "idx_dn_ware",    columnList = "ware_id"),
           @Index(name = "idx_dn_status",  columnList = "status"),
           @Index(name = "idx_dn_product", columnList = "product_id"),
       })
public class DeliveryNote {

    public enum Status {
        PENDING, DISPATCHED, DELIVERED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "ware_id", nullable = false)
    private Long wareId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;

    @Column(name = "dispatched_at")
    private LocalDateTime dispatchedAt;

    @Column(name = "dispatched_by")
    private String dispatchedBy;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    @Column(name = "delivered_by")
    private String deliveredBy;

    @Column(name = "notes", length = 500)
    private String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // ── getters / setters ────────────────────────────────
    public Long          getId()                        { return id; }
    public Long          getOrderId()                   { return orderId; }
    public void          setOrderId(Long v)             { this.orderId = v; }
    public Long          getWareId()                    { return wareId; }
    public void          setWareId(Long v)              { this.wareId = v; }
    public Long          getProductId()                 { return productId; }
    public void          setProductId(Long v)           { this.productId = v; }
    public Integer       getQuantity()                  { return quantity; }
    public void          setQuantity(Integer v)         { this.quantity = v; }
    public Status        getStatus()                    { return status; }
    public void          setStatus(Status v)            { this.status = v; }
    public LocalDateTime getDispatchedAt()              { return dispatchedAt; }
    public void          setDispatchedAt(LocalDateTime v){ this.dispatchedAt = v; }
    public String        getDispatchedBy()              { return dispatchedBy; }
    public void          setDispatchedBy(String v)      { this.dispatchedBy = v; }
    public LocalDateTime getDeliveredAt()               { return deliveredAt; }
    public void          setDeliveredAt(LocalDateTime v){ this.deliveredAt = v; }
    public String        getDeliveredBy()               { return deliveredBy; }
    public void          setDeliveredBy(String v)       { this.deliveredBy = v; }
    public String        getNotes()                     { return notes; }
    public void          setNotes(String v)             { this.notes = v; }
    public LocalDateTime getCreatedAt()                 { return createdAt; }
}