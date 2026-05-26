package com.harshi_solution.warehouse.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harshi_solution.auth.exception.NotFoundException;
import com.harshi_solution.warehouse.builder.InboundSessionBuilder;
import com.harshi_solution.warehouse.builder.InboundToteBuilder;
import com.harshi_solution.warehouse.builder.InboundToteItemBuilder;
import com.harshi_solution.warehouse.client.ProductClient;
import com.harshi_solution.warehouse.dto.AssignProductRequest;
import com.harshi_solution.warehouse.dto.CreateSessionRequest;
import com.harshi_solution.warehouse.dto.InboundSessionDto;
import com.harshi_solution.warehouse.dto.InboundToteDto;
import com.harshi_solution.warehouse.dto.InboundToteItemDto;
import com.harshi_solution.warehouse.dto.PlaceItemRequest;
import com.harshi_solution.warehouse.dto.ScanLpnRequest;
import com.harshi_solution.warehouse.dto.ScanSkuRequest;
import com.harshi_solution.warehouse.entities.BinLocation;
import com.harshi_solution.warehouse.entities.InboundSession;
import com.harshi_solution.warehouse.entities.InboundStatus;
import com.harshi_solution.warehouse.entities.InboundTote;
import com.harshi_solution.warehouse.entities.InboundToteItem;
import com.harshi_solution.warehouse.entities.ItemStatus;
import com.harshi_solution.warehouse.entities.ToteStatus;
import com.harshi_solution.warehouse.exception.BusinessException;
import com.harshi_solution.warehouse.repo.BinLocationRepository;
import com.harshi_solution.warehouse.repo.InboundSessionRepository;
import com.harshi_solution.warehouse.repo.InboundToteItemRepository;
import com.harshi_solution.warehouse.repo.InboundToteRepository;
import com.harshi_solution.warehouse.repo.ProductBinAssignmentRepository;

@Service
@Transactional
public class InboundService {

    private final InboundSessionRepository sessionRepo;
    private final InboundToteRepository toteRepo;
    private final InboundToteItemRepository itemRepo;
    private final BinLocationRepository binRepo;
    private final ProductBinAssignmentRepository assignRepo;
    private final BinLocationService binService;
    private final ProductClient productClient;

    InboundService(InboundSessionRepository inboundSessionRepository, InboundToteRepository inboundToteRepository,
            InboundToteItemRepository itemRepo, BinLocationRepository binRepo,
            ProductBinAssignmentRepository assignRepo, BinLocationService binService,
            ProductClient productClient) {
        this.sessionRepo = inboundSessionRepository;
        this.toteRepo = inboundToteRepository;
        this.itemRepo = itemRepo;
        this.binRepo = binRepo;
        this.assignRepo = assignRepo;
        this.binService = binService;
        this.productClient = productClient;
    }

    // ═══════════════════════════════════════════════════════
    // SESSIONS
    // ═══════════════════════════════════════════════════════

    /**
     * Admin creates a session when a truck arrives.
     */
    public InboundSessionDto createSession(
            CreateSessionRequest req, String createdBy) {

        InboundSession session = new InboundSessionBuilder()
                .wareId(req.wareId())
                .truckRef(req.truckRef())
                .status(InboundStatus.OPEN)
                .createdBy(createdBy)
                .build();

        return toSessionDto(sessionRepo.save(session));
    }

    /**
     * Returns all sessions for a warehouse, newest first.
     */
    @Transactional(readOnly = true)
    public List<InboundSessionDto> getSessions(Long wareId) {
        return sessionRepo
                .findByWareIdOrderByCreatedAtDesc(wareId)
                .stream()
                .map(this::toSessionDto)
                .toList();
    }

    /**
     * Returns open + in-progress sessions for a warehouse.
     * Used by the loader dashboard to show active work.
     */
    @Transactional(readOnly = true)
    public List<InboundSessionDto> getActiveSessions(Long wareId) {
        return sessionRepo
                .findByWareIdAndStatusOrderByCreatedAtDesc(wareId, InboundStatus.OPEN)
                .stream()
                .map(this::toSessionDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public InboundSessionDto getSession(Long sessionId) {
        return toSessionDto(findSessionOrThrow(sessionId));
    }

    /**
     * Marks a session completed.
     * Called automatically when last tote completes,
     * or manually by admin if needed.
     */
    public InboundSessionDto completeSession(Long sessionId) {
        InboundSession session = findSessionOrThrow(sessionId);

        if (session.getStatus() == InboundStatus.COMPLETED)
            throw new BusinessException("Session is already completed");

        session.setStatus(InboundStatus.COMPLETED);
        session.setCompletedAt(LocalDateTime.now());
        return toSessionDto(sessionRepo.save(session));
    }

    // ═══════════════════════════════════════════════════════
    // TOTES
    // ═══════════════════════════════════════════════════════

    /**
     * Loader scans an LPN barcode — creates or retrieves the tote.
     * Idempotent: scanning the same LPN twice returns the same tote.
     */
    public InboundToteDto scanLpn(Long sessionId, ScanLpnRequest req) {
        InboundSession session = findSessionOrThrow(sessionId);

        // Idempotent — return existing tote if already scanned
        return toteRepo
                .findBySession_IdAndLpn(sessionId, req.lpn())
                .map(this::toToteDto)
                .orElseGet(() -> {
                    // First scan — create the tote
                    InboundTote tote = new InboundToteBuilder()
                            .session(session)
                            .lpn(req.lpn())
                            .status(ToteStatus.PENDING)
                            .build();

                    InboundTote saved = toteRepo.save(tote);

                    // Move session to IN_PROGRESS on first tote
                    if (session.getStatus() == InboundStatus.OPEN) {
                        session.setStatus(InboundStatus.IN_PROGRESS);
                        sessionRepo.save(session);
                    }

                    return toToteDto(saved);
                });
    }

    @Transactional(readOnly = true)
    public List<InboundToteDto> getTotes(Long sessionId) {
        return toteRepo
                .findBySession_Id(sessionId)
                .stream()
                .map(this::toToteDto)
                .toList();
    }

    // ═══════════════════════════════════════════════════════
    // ITEMS
    // ═══════════════════════════════════════════════════════

    /**
     * Loader scans a SKU QR code inside a tote.
     * Creates a tote item linking product → bin.
     *
     * The bin must already be assigned to this product
     * (via BinManagementPage) OR the loader selects a free
     * bin from the suggested list.
     *
     * Stock is NOT updated here — only when placement is
     * confirmed via placeItem(). This prevents phantom stock
     * if the loader scans but does not physically place.
     */
    public InboundToteItemDto scanSku(Long toteId, ScanSkuRequest req) {
        InboundTote tote = findToteOrThrow(toteId);

        if (tote.getStatus() == ToteStatus.COMPLETED)
            throw new BusinessException(
                    "Tote " + tote.getLpn() + " is already completed");

        BinLocation bin = binRepo.findById(req.binId())
                .orElseThrow(() -> new NotFoundException(
                        "Bin not found: " + req.binId()));

        // Move tote to SCANNING on first item
        if (tote.getStatus() == ToteStatus.PENDING) {
            tote.setStatus(ToteStatus.SCANNING);
            toteRepo.save(tote);
        }

        InboundToteItem item = new InboundToteItemBuilder()
                .tote(tote)
                .productId(req.productId())
                .receivedQuantity(req.quantity())
                .bin(bin)
                .status(ItemStatus.PENDING)
                .build();

        InboundToteItem saved = itemRepo.save(item);
        return toItemDto(saved, resolveProductName(req.productId()));
    }

    @Transactional(readOnly = true)
    public List<InboundToteItemDto> getToteItems(Long toteId) {
        return itemRepo
                .findByTote_Id(toteId)
                .stream()
                .map(item -> toItemDto(item, resolveProductName(item.getProductId())))
                .toList();
    }

    /**
     * Loader confirms physical placement of an item in its bin.
     *
     * This is the moment inventory is updated:
     * 1. Mark item PLACED
     * 2. Adjust bin stock quantity upward
     * 3. If bin has no assignment for this product, create one
     * 4. Check if tote is now fully placed → auto-complete
     * 5. Check if session is now fully complete → auto-complete
     */
    public InboundToteItemDto placeItem(
            Long itemId, PlaceItemRequest req) {

        InboundToteItem item = itemRepo.findById(itemId)
                .orElseThrow(() -> new NotFoundException(
                        "Item not found: " + itemId));

        if (item.getStatus() == ItemStatus.PLACED)
            throw new BusinessException("Item already marked as placed");

        // ── 1. Mark item placed ───────────────────────────
        item.setStatus(ItemStatus.PLACED);
        item.setPlacedAt(LocalDateTime.now());
        item.setPlacedBy(req.placedBy());
        itemRepo.save(item);

        // ── 2 & 3. Update bin stock ───────────────────────
        BinLocation bin = item.getBin();
        Long productId = item.getProductId();
        int qty = item.getReceivedQuantity();

        assignRepo
                .findByProductIdAndBin_Id(productId, bin.getId())
                .ifPresentOrElse(
                        // Assignment exists → adjust quantity
                        existing -> binService.adjustQuantity(existing.getId(), qty),

                        // No assignment → create one and mark bin occupied
                        () -> {
                            InboundToteItem i = item; // effectively final
                            binService.assignProduct(bin.getId(),
                                    new AssignProductRequest(productId, qty));
                        });

        // ── 4. Auto-complete tote if all items placed ─────
        InboundTote tote = item.getTote();
        long pendingItems = itemRepo
                .countByTote_IdAndStatus(tote.getId(), ItemStatus.PENDING);

        if (pendingItems == 0) {
            tote.setStatus(ToteStatus.COMPLETED);
            tote.setCompletedAt(LocalDateTime.now());
            toteRepo.save(tote);

            // ── 5. Auto-complete session if all totes done ─
            long pendingTotes = toteRepo
                    .countBySession_IdAndStatus(
                            tote.getSession().getId(), ToteStatus.COMPLETED);
            long totalTotes = toteRepo
                    .findBySession_Id(tote.getSession().getId()).size();

            if (pendingTotes == totalTotes) {
                completeSession(tote.getSession().getId());
            }
        }

        return toItemDto(item, resolveProductName(productId));
    }

    // ═══════════════════════════════════════════════════════
    // HELPERS
    // ═══════════════════════════════════════════════════════

    private InboundSession findSessionOrThrow(Long id) {
        return sessionRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Inbound session not found: " + id));
    }

    private InboundTote findToteOrThrow(Long id) {
        return toteRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Tote not found: " + id));
    }

    private String resolveProductName(Long productId) {
        try {
            return productClient.getProductName(productId);
        } catch (Exception e) {
            return "Product #" + productId;
        }
    }

    // ═══════════════════════════════════════════════════════
    // MAPPERS
    // ═══════════════════════════════════════════════════════

    private InboundSessionDto toSessionDto(InboundSession s) {
        List<InboundTote> totes = toteRepo.findBySession_Id(s.getId());
        int completed = (int) totes.stream()
                .filter(t -> t.getStatus() == ToteStatus.COMPLETED)
                .count();

        return new InboundSessionDto(
                s.getId(), s.getWareId(), s.getTruckRef(),
                s.getStatus().name(), s.getCreatedBy(),
                s.getCreatedAt(), s.getCompletedAt(),
                totes.size(), completed);
    }

    private InboundToteDto toToteDto(InboundTote t) {
        List<InboundToteItem> items = itemRepo.findByTote_Id(t.getId());
        int placed = (int) items.stream()
                .filter(i -> i.getStatus() == ItemStatus.PLACED)
                .count();

        return new InboundToteDto(
                t.getId(), t.getSession().getId(), t.getLpn(),
                t.getStatus().name(), t.getScannedAt(),
                items.size(), placed);
    }

    private InboundToteItemDto toItemDto(InboundToteItem i, String productName) {
        return new InboundToteItemDto(
                i.getId(), i.getTote().getId(),
                i.getProductId(), productName,
                i.getReceivedQuantity(),
                i.getBin().getId(), i.getBin().getBinCode(),
                i.getStatus().name(),
                i.getPlacedAt(), i.getPlacedBy());
    }
}
