package com.harshi_solution.party.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harshi_solution.party.dto.BaseUIResponse;
import com.harshi_solution.party.dto.PartyRequestDTO;
import com.harshi_solution.party.dto.PartyResponseDTO;
import com.harshi_solution.party.service.PartyService;
import com.harshi_solution.party.util.ResponseBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/parties")
public class PartyController {

    private final PartyService partyService;

    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    @PostMapping
    public BaseUIResponse<PartyResponseDTO> createParty(
            @Valid @RequestBody PartyRequestDTO request) {

        PartyResponseDTO response = partyService.saveParty(request);

        return ResponseBuilder.success("Party created successfully", response);
    }

    @GetMapping
    public BaseUIResponse<List<PartyResponseDTO>> getAllParties() {

        List<PartyResponseDTO> parties = partyService.getPartyList();

        return ResponseBuilder.success("Parties fetched successfully", parties);
    }

    @GetMapping("/{partyId}")
    public BaseUIResponse<PartyResponseDTO> getPartyById(
            @PathVariable Long partyId) {

        PartyResponseDTO party = partyService.getPartyById(partyId);

        return ResponseBuilder.success("Party fetched successfully", party);
    }

    @PutMapping("/{partyId}")
    public BaseUIResponse<PartyResponseDTO> updateParty(
            @PathVariable Long partyId,
            @Valid @RequestBody PartyRequestDTO request) {

        PartyResponseDTO updatedParty = partyService.updateParty(partyId, request);

        return ResponseBuilder.success("Party updated successfully", updatedParty);
    }

    @DeleteMapping("/{partyId}")
    public BaseUIResponse<Void> deleteParty(@PathVariable Long partyId) {

        partyService.deleteParty(partyId);

        return ResponseBuilder.success("Party deleted successfully", null);
    }
}