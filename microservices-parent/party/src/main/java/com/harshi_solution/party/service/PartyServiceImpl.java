package com.harshi_solution.party.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harshi_solution.party.dto.PartyRequestDTO;
import com.harshi_solution.party.dto.PartyResponseDTO;
import com.harshi_solution.party.entities.Party;
import com.harshi_solution.party.mapper.PartyMapper;
import com.harshi_solution.party.repo.PartyRepository;
@Service
@Transactional
public class PartyServiceImpl implements PartyService {

    private final PartyRepository partyRepository;
    private final PartyMapper partyMapper;

    public PartyServiceImpl(PartyRepository partyRepository, PartyMapper partyMapper){
        this.partyRepository=partyRepository;
        this.partyMapper=partyMapper;
    }

    @Override
    public PartyResponseDTO saveParty(PartyRequestDTO request) {

        if (partyRepository.existsByPartyName(request.getPartyName())) {
            throw new RuntimeException("Party with same name already exists");
        }

        Party party = partyMapper.toEntity(request);

        Party savedParty = partyRepository.save(party);

        return partyMapper.toDTO(savedParty);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PartyResponseDTO> getPartyList() {

        List<Party> parties = partyRepository.findAll();

        return partyMapper.toDTOList(parties);
    }

    @Override
    @Transactional(readOnly = true)
    public PartyResponseDTO getPartyById(Long partyId) {

        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new RuntimeException("Party not found"));

        return partyMapper.toDTO(party);
    }

    @Override
    public PartyResponseDTO updateParty(Long partyId, PartyRequestDTO request) {

        Party existingParty = partyRepository.findById(partyId)
                .orElseThrow(() -> new RuntimeException("Party not found"));

        existingParty.setPartyName(request.getPartyName());
        existingParty.setPartyLocation(request.getPartyLocation());
        existingParty.setPhoneNo(request.getPhoneNo());

        Party updatedParty = partyRepository.save(existingParty);

        return partyMapper.toDTO(updatedParty);
    }

    @Override
    public void deleteParty(Long partyId) {

        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new RuntimeException("Party not found"));

        partyRepository.delete(party);
    }
}