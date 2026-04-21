package com.harshi_solution.party.service;

import java.util.List;

import org.springframework.lang.NonNull;

import com.harshi_solution.party.dto.PartyRequestDTO;
import com.harshi_solution.party.dto.PartyResponseDTO;

public interface PartyService {

    PartyResponseDTO saveParty(@NonNull PartyRequestDTO party);

    List<PartyResponseDTO> getPartyList();

    PartyResponseDTO getPartyById(@NonNull Long partyId);

    PartyResponseDTO updateParty(@NonNull Long partyId, PartyRequestDTO party);

    void deleteParty(@NonNull Long partyId);
}
