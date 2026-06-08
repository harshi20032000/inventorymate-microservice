package com.harshi_solution.warehouse.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harshi_solution.auth.dto.BaseUIResponse;
import com.harshi_solution.auth.util.ResponseBuilder;
import com.harshi_solution.warehouse.dto.AdjustQuantityRequest;
import com.harshi_solution.warehouse.dto.AssignProductRequest;
import com.harshi_solution.warehouse.dto.BinLocationDto;
import com.harshi_solution.warehouse.dto.CreateBinRequest;
import com.harshi_solution.warehouse.dto.ProductBinAssignmentDto;
import com.harshi_solution.warehouse.service.BinLocationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/location")
public class BinLocationController {

    private final BinLocationService binLocationService;

    public BinLocationController(BinLocationService binLocationService) {
        this.binLocationService = binLocationService;
    }

    // ── Bin CRUD ─────────────────────────────────────────────

    @GetMapping("/{wareId}/bins")
    public BaseUIResponse<List<BinLocationDto>> getBins(@PathVariable Long wareId) {

        return ResponseBuilder.success("Bins fetched successfully", binLocationService.getBins(wareId));
    }

    @PostMapping("/{wareId}/bins")
    public BaseUIResponse<BinLocationDto> createBin(
            @PathVariable Long wareId,
            @Valid @RequestBody CreateBinRequest request) {
        return ResponseBuilder.success("Bin created", binLocationService.createBin(wareId, request));
    }

    @DeleteMapping("/bins/{binId}")
    public BaseUIResponse<String> deleteBin(@PathVariable Long binId) {
        binLocationService.deleteBin(binId);
        return ResponseBuilder.success("Bin deleted", binId.toString());
    }

    // ── Product assignments ───────────────────────────────────

    @GetMapping("/bins/{binId}/assignments")
    public BaseUIResponse<List<ProductBinAssignmentDto>> getAssignments(
            @PathVariable Long binId) {
        return ResponseBuilder.success("assignments fetched with binId", binLocationService.getAssignments(binId));
    }

    @PostMapping("/bins/{binId}/assign")
    public BaseUIResponse<ProductBinAssignmentDto> assignProduct(
            @PathVariable Long binId,
            @Valid @RequestBody AssignProductRequest request) {
        return ResponseBuilder.success("product assigned", binLocationService.assignProduct(binId, request));
    }

    @DeleteMapping("/assignments/{assignmentId}")
    public BaseUIResponse<String> removeAssignment(
            @PathVariable Long assignmentId) {
        binLocationService.removeAssignment(assignmentId);
        return ResponseBuilder.success("Assignment removed", assignmentId.toString());
    }

    // ── Lookup by warehouse — all assignments ─────────────────

    @GetMapping("/{wareId}/assignments")
    public BaseUIResponse<List<ProductBinAssignmentDto>> getWarehouseAssignments(
            @PathVariable Long wareId) {
        return ResponseBuilder.success("warehouse assignments fetched",
                binLocationService.getWarehouseAssignments(wareId));
    }

    // Add to existing controller

    @GetMapping("/{wareId}/bins/free")
    public BaseUIResponse<List<BinLocationDto>> getFreeBins(
            @PathVariable Long wareId) {
        return ResponseBuilder.success("free bins fetched", binLocationService.getFreeBins(wareId));
    }

    @GetMapping("/bins/{binId}/code")
    public BaseUIResponse<BinLocationDto> getBinByCode(
            @PathVariable Long wareId,
            @RequestParam String binCode) {
        return ResponseBuilder.success("bin fetched by code", binLocationService.getBinByCode(wareId, binCode));
    }

    @PatchMapping("/assignments/{assignmentId}/adjust")
    public BaseUIResponse<ProductBinAssignmentDto> adjustQuantity(
            @PathVariable Long assignmentId,
            @RequestBody AdjustQuantityRequest request) {
        return ResponseBuilder.success("quantity adjusted",
                binLocationService.adjustQuantity(assignmentId, request.delta()));
    }

    @DeleteMapping("/assignments/{assignmentId}/force")
    public BaseUIResponse<String> forceRemove(
            @PathVariable Long assignmentId) {
        binLocationService.forceRemoveAssignment(assignmentId);
        return ResponseBuilder.success("Assignment force-removed", assignmentId.toString());
    }
}
