package com.harshi_solution.party.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.harshi_solution.party.dto.PartyRequestDTO;
import com.harshi_solution.party.dto.PartyResponseDTO;
import com.harshi_solution.party.entities.Party;

@Mapper(componentModel = "spring")
public interface PartyMapper {

    Party toEntity(PartyRequestDTO dto);

    PartyResponseDTO toDTO(Party party);

    List<PartyResponseDTO> toDTOList(List<Party> parties);
}
