package com.harshi_solution.user.service;

import java.util.List;

import com.harshi_solution.user.dto.RepRequestDTO;
import com.harshi_solution.user.dto.RepResponseDTO;

public interface RepsService {

    List<RepResponseDTO> getRepsList();

    RepResponseDTO getRepsById(Long repId);

    RepResponseDTO updateRep(Long repId, RepRequestDTO repRequest);

    void deleteRepById(Long repId);

}
