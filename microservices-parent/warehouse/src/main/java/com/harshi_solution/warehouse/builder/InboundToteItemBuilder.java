package com.harshi_solution.warehouse.builder;


import java.time.LocalDateTime;
import java.util.Objects;

import com.harshi_solution.warehouse.entities.BinLocation;
import com.harshi_solution.warehouse.entities.InboundTote;
import com.harshi_solution.warehouse.entities.InboundToteItem;
import com.harshi_solution.warehouse.entities.ItemStatus;

public class InboundToteItemBuilder {

    private InboundTote tote;
    private Long productId;
    private int receivedQuantity;
    private BinLocation bin;
    private ItemStatus status = ItemStatus.PENDING;
    private LocalDateTime placedAt;
    private String placedBy;

    public InboundToteItemBuilder tote(InboundTote tote) {
        this.tote = tote;
        return this;
    }

    public InboundToteItemBuilder productId(Long productId) {
        this.productId = productId;
        return this;
    }

    public InboundToteItemBuilder receivedQuantity(int receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
        return this;
    }

    public InboundToteItemBuilder bin(BinLocation bin) {
        this.bin = bin;
        return this;
    }

    public InboundToteItemBuilder status(ItemStatus status) {
        this.status = status;
        return this;
    }

    public InboundToteItemBuilder placedAt(LocalDateTime placedAt) {
        this.placedAt = placedAt;
        return this;
    }

    public InboundToteItemBuilder placedBy(String placedBy) {
        this.placedBy = placedBy;
        return this;
    }

    public InboundToteItem build() {

        Objects.requireNonNull(tote, "tote is required");
        Objects.requireNonNull(productId, "productId is required");
        Objects.requireNonNull(bin, "bin is required");

        if (receivedQuantity <= 0) {
            throw new IllegalArgumentException(
                    "receivedQuantity must be greater than 0"
            );
        }

        if (placedAt == null) {
            placedAt = LocalDateTime.now();
        }

        return new InboundToteItem(
                tote,
                productId,
                receivedQuantity,
                bin,
                status,
                placedAt,
                placedBy
        );
    }
}
