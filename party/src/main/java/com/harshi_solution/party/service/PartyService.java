package com.harshi_solution.party.service;

import java.util.List;

import com.harshi_solution.party.dto.PartyRequestDTO;
import com.harshi_solution.party.dto.PartyResponseDTO;

public interface PartyService {

    PartyResponseDTO saveParty(PartyRequestDTO party);

    List<PartyResponseDTO> getPartyList();

    PartyResponseDTO getPartyById(Long partyId);

    PartyResponseDTO updateParty(Long partyId, PartyRequestDTO party);

    void deleteParty(Long partyId);
}
