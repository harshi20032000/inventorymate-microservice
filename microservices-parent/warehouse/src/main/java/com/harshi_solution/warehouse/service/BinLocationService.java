package com.harshi_solution.warehouse.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harshi_solution.auth.exception.NotFoundException;
import com.harshi_solution.warehouse.client.ProductClient;
import com.harshi_solution.warehouse.dto.AssignProductRequest;
import com.harshi_solution.warehouse.dto.BinLocationDto;
import com.harshi_solution.warehouse.dto.CreateBinRequest;
import com.harshi_solution.warehouse.dto.ProductBinAssignmentDto;
import com.harshi_solution.warehouse.entities.BinLocation;
import com.harshi_solution.warehouse.entities.ProductBinAssignment;
import com.harshi_solution.warehouse.exception.BusinessException;
import com.harshi_solution.warehouse.repo.BinLocationRepository;
import com.harshi_solution.warehouse.repo.ProductBinAssignmentRepository;

@Service
public class BinLocationService {

    private final BinLocationRepository binRepo;
    private final ProductBinAssignmentRepository assignRepo;
    private final ProductClient productClient;

    public BinLocationService(BinLocationRepository binRepo, ProductBinAssignmentRepository assignRepo,
            ProductClient productClient) {
        this.binRepo = binRepo;
        this.assignRepo = assignRepo;
        this.productClient = productClient;
    }

    /**
     * Returns all bins for a warehouse ordered by binCode.
     * Includes both occupied and free bins — the UI decides
     * what to show based on the occupied flag.
     */
    public List<BinLocationDto> getBins(Long wareId) {
        return binRepo
                .findByWareId(wareId)
                .stream()
                .map(this::toDto)
                .sorted(Comparator.comparing(BinLocationDto::binCode))
                .toList();
    }

    /**
     * Returns only free (unoccupied) bins for a warehouse.
     * Used by inbound receiving (Phase 2) to suggest putaway
     * locations for newly arrived stock.
     */
    public List<BinLocationDto> getFreeBins(Long wareId) {
        return binRepo
                .findByWareIdAndOccupied(wareId, false)
                .stream()
                .map(this::toDto)
                .sorted(Comparator.comparing(BinLocationDto::binCode))
                .toList();
    }

    /**
     * Creates a new bin for a warehouse.
     *
     * Validates:
     * - Aisle is a single letter A–Z
     * - Rack is numeric
     * - Slot is a single letter
     * - binCode is unique within the warehouse
     *
     * New bins start as unoccupied.
     */
    @Transactional
    public BinLocationDto createBin(Long wareId, CreateBinRequest request) {

        String binCode = buildBinCode(
                request.aisle(), request.rack(), request.slot());

        boolean exists = binRepo
                .findByWareIdAndBinCode(wareId, binCode)
                .isPresent();

        if (exists) {
            throw new BusinessException(
                    "Bin " + binCode + " already exists in warehouse " + wareId);
        }

        BinLocation bin = new BinLocation();
        bin.setWareId(wareId);
        bin.setAisle(request.aisle().toUpperCase());
        bin.setRack(request.rack());
        bin.setSlot(request.slot().toUpperCase());
        bin.setBinCode(binCode);
        bin.setOccupied(false); // new bins always start free

        return toDto(binRepo.save(bin));
    }

    /**
     * Deletes a bin.
     *
     * Rules:
     * - Cannot delete an occupied bin — it holds live stock.
     * - Unoccupied bins with no assignment history → hard delete.
     * - Unoccupied bins with assignment history → also hard delete,
     * since assignment records are the audit trail, not the bin itself.
     *
     * If you need to delete an occupied bin (data correction),
     * first remove the assignment via forceRemoveAssignment(),
     * which marks the bin unoccupied, then delete.
     */
    @Transactional
    public void deleteBin(Long binId) {
        BinLocation bin = findBinOrThrow(binId);

        if (bin.isOccupied()) {
            throw new BusinessException(
                    "Cannot delete bin " + bin.getBinCode() +
                            " — it is currently occupied. " +
                            "Remove the product assignment first.");
        }

        binRepo.delete(bin);
    }

    /**
     * Looks up a bin by its scanned code within a warehouse.
     * Called by the inbound scanner and packer page.
     *
     * @throws NotFoundException if not found
     */
    public BinLocationDto getBinByCode(Long wareId, String binCode) {
        return binRepo
                .findByWareIdAndBinCode(wareId, binCode.toUpperCase())
                .map(this::toDto)
                .orElseThrow(() -> new NotFoundException(
                        "Bin not found: " + binCode + " in warehouse " + wareId));
    }

    // ═══════════════════════════════════════════════════════════
    // PRODUCT ASSIGNMENTS
    // ═══════════════════════════════════════════════════════════

    /**
     * Returns all assignments for a specific bin,
     * enriched with product names from product-service.
     */
    public List<ProductBinAssignmentDto> getAssignments(Long binId) {
        findBinOrThrow(binId);
        return assignRepo
                .findByBin_Id(binId)
                .stream()
                .map(a -> toAssignmentDto(a, resolveProductName(a.getProductId())))
                .toList();
    }

    /**
     * Returns all assignments across every bin in a warehouse.
     * Single call — avoids N+1 from the frontend calling
     * getAssignments() per bin card.
     */
    public List<ProductBinAssignmentDto> getWarehouseAssignments(Long wareId) {
        return assignRepo
                .findByBin_WareId(wareId)
                .stream()
                .map(a -> toAssignmentDto(a, resolveProductName(a.getProductId())))
                .toList();
    }

    /**
     * Assigns a product to a bin.
     *
     * Occupancy rules (replaces the old capacity system):
     * - A bin can only hold ONE product assignment at a time.
     * (single-SKU-per-bin policy — standard in slot-based WMS)
     * - If the bin is already occupied, reject with a clear error
     * telling the caller which product is already there.
     * - On success, mark the bin as occupied.
     *
     * Why single-SKU?
     * Mixed bins (multiple SKUs in one bin) cause pick errors.
     * Packers scan a bin expecting one product — if two products
     * are there, they must verify visually, which defeats the
     * purpose of scan-based picking.
     */
    @Transactional
    public ProductBinAssignmentDto assignProduct(Long binId, AssignProductRequest request) {

        BinLocation bin = findBinOrThrow(binId);

        if (bin.isOccupied()) {
            // Find what is in there so the error is actionable
            String occupant = assignRepo
                    .findByBin_Id(binId)
                    .stream()
                    .findFirst()
                    .map(a -> resolveProductName(a.getProductId()))
                    .orElse("unknown product");

            throw new BusinessException(
                    "Bin " + bin.getBinCode() + " is occupied by " + occupant +
                            ". A bin can only hold one product. " +
                            "Remove the existing assignment first.");
        }

        // Guard: same product already assigned (defensive, normally
        // caught by the occupied check above, but the unique constraint
        // makes this belt-and-suspenders)
        assignRepo
                .findByProductIdAndBin_Id(request.productId(), binId)
                .ifPresent(existing -> {
                    throw new BusinessException(
                            "Product " + request.productId() +
                                    " is already assigned to bin " + bin.getBinCode());
                });

        ProductBinAssignment assignment = new ProductBinAssignment();
        assignment.setProductId(request.productId());
        assignment.setBin(bin);
        assignment.setQuantity(request.quantity());

        ProductBinAssignment saved = assignRepo.save(assignment);

        // Mark bin occupied
        bin.setOccupied(true);
        binRepo.save(bin);

        return toAssignmentDto(saved, resolveProductName(request.productId()));
    }

    /**
     * Adjusts the stock quantity in a bin by a signed delta.
     *
     * delta > 0 → stock added (inbound receipt, Phase 2)
     * delta < 0 → stock removed (pick, dispatch)
     *
     * If adjustment brings quantity to zero, the bin is
     * automatically marked unoccupied — it is now free for
     * a new product assignment.
     *
     * Using delta instead of setQuantity() is intentional:
     * concurrent inbound sessions both do quantity + N,
     * not quantity = N, so writes are safely additive.
     */
    @Transactional
    public ProductBinAssignmentDto adjustQuantity(Long assignmentId, int delta) {

        ProductBinAssignment assignment = assignRepo
                .findById(assignmentId)
                .orElseThrow(() -> new NotFoundException(
                        "Assignment not found: " + assignmentId));

        int newQty = assignment.getQuantity() + delta;

        if (newQty < 0) {
            throw new BusinessException(String.format(
                    "Adjustment would result in negative quantity. " +
                            "Current: %d, delta: %d",
                    assignment.getQuantity(), delta));
        }

        assignment.setQuantity(newQty);
        ProductBinAssignment saved = assignRepo.save(assignment);

        // Auto-vacate the bin when stock hits zero
        if (newQty == 0) {
            BinLocation bin = assignment.getBin();
            bin.setOccupied(false);
            binRepo.save(bin);
        }

        return toAssignmentDto(saved, resolveProductName(saved.getProductId()));
    }

    /**
     * Removes a product assignment from a bin.
     *
     * Guard: only allowed when quantity is zero.
     * Prevents removing an assignment while the bin physically
     * holds stock — the database would say empty but the shelf
     * would not be.
     *
     * If the assignment quantity was zeroed by adjustQuantity(),
     * the bin is already marked unoccupied. This call then
     * cleans up the assignment record itself.
     */
    @Transactional
    public void removeAssignment(Long assignmentId) {

        ProductBinAssignment assignment = assignRepo
                .findById(assignmentId)
                .orElseThrow(() -> new NotFoundException(
                        "Assignment not found: " + assignmentId));

        if (assignment.getQuantity() > 0) {
            throw new BusinessException(String.format(
                    "Cannot remove assignment — bin %s still holds %d units of %s. " +
                            "Zero out the quantity via stock adjustment first.",
                    assignment.getBin().getBinCode(),
                    assignment.getQuantity(),
                    resolveProductName(assignment.getProductId())));
        }

        assignRepo.delete(assignment);

        // Ensure bin is unoccupied — it should already be from
        // adjustQuantity() auto-vacate, but this is defensive
        BinLocation bin = assignment.getBin();
        if (bin.isOccupied()) {
            bin.setOccupied(false);
            binRepo.save(bin);
        }
    }

    /**
     * Force-removes an assignment regardless of quantity.
     * Admin-only — bypasses the zero-quantity guard.
     * For data corrections only.
     */
    @Transactional
    public void forceRemoveAssignment(Long assignmentId) {

        ProductBinAssignment assignment = assignRepo
                .findById(assignmentId)
                .orElseThrow(() -> new NotFoundException(
                        "Assignment not found: " + assignmentId));

        BinLocation bin = assignment.getBin();
        assignRepo.delete(assignment);

        // Vacate the bin regardless of quantity
        bin.setOccupied(false);
        binRepo.save(bin);
    }

    // ═══════════════════════════════════════════════════════════
    // INTERNAL HELPERS
    // ═══════════════════════════════════════════════════════════

    private BinLocation findBinOrThrow(Long binId) {
        return binRepo.findById(binId)
                .orElseThrow(() -> new NotFoundException(
                        "Bin not found: " + binId));
    }

    /**
     * Builds and validates a bin code from its component parts.
     * Validation here means the frontend preview and the
     * persisted binCode always agree on format.
     */
    private String buildBinCode(String aisle, String rack, String slot) {
        if (aisle == null || !aisle.trim().matches("[A-Za-z]"))
            throw new BusinessException("Aisle must be a single letter (A–Z)");

        if (rack == null || !rack.trim().matches("\\d+"))
            throw new BusinessException("Rack must be numeric");

        if (slot == null || !slot.trim().matches("[A-Za-z]"))
            throw new BusinessException("Slot must be a single letter");

        return aisle.trim().toUpperCase()
                + "-" + rack.trim()
                + "-" + slot.trim().toUpperCase();
    }

    /**
     * Resolves a product name from product-service.
     * Never throws — a failed name lookup returns a safe fallback
     * because a product name is display data, not a business rule.
     * A bin operation should never fail because of a name resolution.
     */
    private String resolveProductName(Long productId) {
        try {
            return productClient.getProductName(productId);
        } catch (Exception e) {
            return "Product #" + productId;
        }
    }

    // ═══════════════════════════════════════════════════════════
    // MAPPERS
    // ═══════════════════════════════════════════════════════════

    private BinLocationDto toDto(BinLocation bin) {
        return new BinLocationDto(
                bin.getId(),
                bin.getWareId(),
                bin.getAisle(),
                bin.getRack(),
                bin.getSlot(),
                bin.getBinCode(),
                bin.isOccupied());
    }

    private ProductBinAssignmentDto toAssignmentDto(
            ProductBinAssignment a,
            String productName) {
        return new ProductBinAssignmentDto(
                a.getId(),
                a.getProductId(),
                productName,
                a.getBin().getId(),
                a.getBin().getBinCode(),
                a.getQuantity(),
                a.getUpdatedAt());
    }
}