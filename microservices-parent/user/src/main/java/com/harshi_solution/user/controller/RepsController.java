package com.harshi_solution.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harshi_solution.user.dto.BaseUIResponse;
import com.harshi_solution.user.dto.RepRequestDTO;
import com.harshi_solution.user.dto.RepResponseDTO;
import com.harshi_solution.user.service.RepsService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/reps")
public class RepsController {

    @Autowired
    private RepsService repsService;

    @Operation(summary = "get all reps", description = "used to get all reps")
    @GetMapping
    public BaseUIResponse<List<RepResponseDTO>> getAllReps() {
        List<RepResponseDTO> result =  repsService.getRepsList();
        BaseUIResponse<List<RepResponseDTO>> baseUIResponse = new BaseUIResponse<>();
        baseUIResponse.setResponsePayload(result);
        return baseUIResponse;
    }

    @Operation(summary = "get reps details by id", description = "used to get reps details by id")
    @GetMapping("/{repId}")
    public BaseUIResponse<RepResponseDTO> getRepDetails(@PathVariable Long repId) {

        RepResponseDTO reps = repsService.getRepsById(repId);
        // List<Order> orders = repsService.getOrderListByReps(repId);

       // BigDecimal totalBill = OrderHelper.totalOrderPrice(orders);
        //BigDecimal remaining = OrderHelper.totalPendingPrice(orders);

       // RepsDetailsResponse response = new RepsDetailsResponse(
                // reps, orders, totalBill, remaining);
        BaseUIResponse<RepResponseDTO> baseUIResponse = new BaseUIResponse<>();
        baseUIResponse.setResponsePayload(reps);
        return baseUIResponse;

    }

     @Operation(summary = "update Rep by Id and Body", description = "used to update Rep by Id and Body")
    @PatchMapping("/{repId}")
    public BaseUIResponse<RepResponseDTO> updateRep(
            @PathVariable Long repId,
            @Valid @RequestBody RepRequestDTO RepRequest) {

        RepResponseDTO updatedRep = repsService.updateRep(repId, RepRequest);

        BaseUIResponse<RepResponseDTO> response = new BaseUIResponse<>();
        response.setResponsePayload(updatedRep);

        return response;
    }

    @Operation(summary = "delete Rep using Id", description = "used to delete Rep using Id")
    @DeleteMapping("/{repId}")
    public BaseUIResponse<String> deleteRep(
            @PathVariable Long repId) {

        repsService.deleteRepById(repId);

        BaseUIResponse<String> response = new BaseUIResponse<>();
        response.setResponsePayload(
                "Rep with ID " + repId + " deleted successfully.");

        return response;
    }
}
