package com.harshi_solution.warehouse.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "inbound_tote_items")
public class InboundToteItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tote_id", nullable = false)
    private InboundTote tote;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "received_quantity", nullable = false)
    private int receivedQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bin_id", nullable = false)
    private BinLocation bin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemStatus status = ItemStatus.PENDING;

    @Column(name = "placed_at")
    private LocalDateTime placedAt;

    @Column(name = "placed_by")
    private String placedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InboundTote getTote() {
        return tote;
    }

    public void setTote(InboundTote tote) {
        this.tote = tote;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(int receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }

    public BinLocation getBin() {
        return bin;
    }

    public void setBin(BinLocation bin) {
        this.bin = bin;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public LocalDateTime getPlacedAt() {
        return placedAt;
    }

    public void setPlacedAt(LocalDateTime placedAt) {
        this.placedAt = placedAt;
    }

    public String getPlacedBy() {
        return placedBy;
    }

    public void setPlacedBy(String placedBy) {
        this.placedBy = placedBy;
    }

    public InboundToteItem(
            InboundTote tote,
            Long productId,
            int receivedQuantity,
            BinLocation bin,
            ItemStatus status,
            LocalDateTime placedAt,
            String placedBy) {
        this.tote = tote;
        this.productId = productId;
        this.receivedQuantity = receivedQuantity;
        this.bin = bin;
        this.status = status;
        this.placedAt = placedAt;
        this.placedBy = placedBy;
    }

    protected InboundToteItem() {
    }

}
