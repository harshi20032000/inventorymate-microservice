package com.harshi_solution.transport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harshi_solution.transport.dto.BaseUIResponse;
import com.harshi_solution.transport.dto.TransportRequestDTO;
import com.harshi_solution.transport.dto.TransportResponseDTO;
import com.harshi_solution.transport.service.TransportService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

/**
 * Controller class for managing transport-related operations.
 */
@RestController
@RequestMapping("/api/v1/transports")
public class TransportController {

    @Autowired
    private TransportService transportService;

    @Operation(summary = "get All Transports", description = "used to get all Transports")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseUIResponse<Page<TransportResponseDTO>> getAllTransports(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<TransportResponseDTO> allTransports = transportService.getAllTransports(page, size);
        BaseUIResponse<Page<TransportResponseDTO>> baseUIResponse = new BaseUIResponse<>();
        baseUIResponse.setResponsePayload(allTransports);
        return baseUIResponse;
    }

    @Operation(summary = "get Transports by Id", description = "used to get get Transports by Id")
    @GetMapping("/{transportId}")
    public BaseUIResponse<TransportResponseDTO> getTransportById(
            @PathVariable @Positive Long transportId) {

        TransportResponseDTO transport = transportService.getTransportById(transportId);

        BaseUIResponse<TransportResponseDTO> response = new BaseUIResponse<>();
        response.setResponsePayload(transport);
        return response;
    }

    @Operation(summary = "create transport", description = "used to create a Transport Resource")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseUIResponse<TransportResponseDTO> createTransport(@RequestBody TransportRequestDTO transportRequest) {
        TransportResponseDTO createdTransport = transportService.saveTransport(transportRequest);
        BaseUIResponse<TransportResponseDTO> baseUIResponse = new BaseUIResponse<>();
        baseUIResponse.setResponsePayload(createdTransport);
        return baseUIResponse;
    }

    @Operation(summary = "update Transport by Id and Body", description = "used to update Transport by Id and Body")
    @PatchMapping("/{transportId}")
    public BaseUIResponse<TransportResponseDTO> updateTransport(
            @PathVariable Long transportId,
            @Valid @RequestBody TransportRequestDTO transportRequest) {

        TransportResponseDTO updatedTransport = transportService.updateTransport(transportId, transportRequest);

        BaseUIResponse<TransportResponseDTO> response = new BaseUIResponse<>();
        response.setResponsePayload(updatedTransport);

        return response;
    }

    @Operation(summary = "delete Transport using Id", description = "used to delete Transport using Id")
    @DeleteMapping("/{transportId}")
    public BaseUIResponse<String> deleteTransport(
            @PathVariable Long transportId) {

        transportService.deleteTransportById(transportId);

        BaseUIResponse<String> response = new BaseUIResponse<>();
        response.setResponsePayload(
                "Transport with ID " + transportId + " deleted successfully.");

        return response;
    }

}
