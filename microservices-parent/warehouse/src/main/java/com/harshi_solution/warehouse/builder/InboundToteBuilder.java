package com.harshi_solution.warehouse.builder;


import java.time.LocalDateTime;
import java.util.Objects;

import com.harshi_solution.warehouse.entities.InboundSession;
import com.harshi_solution.warehouse.entities.InboundTote;
import com.harshi_solution.warehouse.entities.ToteStatus;

public class InboundToteBuilder {

    private InboundSession session;
    private String lpn;
    private ToteStatus status = ToteStatus.PENDING;
    private LocalDateTime scannedAt;
    private LocalDateTime completedAt;


    public InboundToteBuilder session(InboundSession session) {
        this.session = session;
        return this;
    }

    public InboundToteBuilder lpn(String lpn) {
        this.lpn = lpn;
        return this;
    }

    public InboundToteBuilder status(ToteStatus status) {
        this.status = status;
        return this;
    }

    public InboundToteBuilder scannedAt(LocalDateTime scannedAt) {
        this.scannedAt = scannedAt;
        return this;
    }

    public InboundTote build() {

        Objects.requireNonNull(session, "session is required");
        Objects.requireNonNull(lpn, "lpn is required");

        if (scannedAt == null) {
            scannedAt = LocalDateTime.now();
        }

        return new InboundTote(
                session,
                lpn,
                status,
                scannedAt, completedAt
        );
    }
}
