package com.harshi_solution.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.harshi_solution.user.dto.RepRequestDTO;
import com.harshi_solution.user.dto.RepResponseDTO;
import com.harshi_solution.user.entities.Reps;
import com.harshi_solution.user.repo.RepsRepository;
import com.harshi_solution.user.repo.UserRegisterEntityRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class RepsServiceImpl implements RepsService {

    private final RepsRepository repsRepository;
    private final UserRegisterEntityRepository userRepository;

    public RepsServiceImpl(RepsRepository repsRepository, UserRegisterEntityRepository userRepository) {
        this.repsRepository = repsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<RepResponseDTO> getRepsList() {

        return repsRepository.findAll()
                .stream()
                .map(rep -> new RepResponseDTO(
                        rep.getRepId(),
                        rep.getRepName(),
                        rep.getRepLocation(),
                        rep.getUser().getUsername()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RepResponseDTO getRepsById(Long repId) {

        Reps rep = repsRepository.findById(repId)
                .orElseThrow(() -> new RuntimeException(
                        "Rep not found with id: " + repId));

        return new RepResponseDTO(
                rep.getRepId(),
                rep.getRepName(),
                rep.getRepLocation(),
                rep.getUser().getUsername());
    }

    @Override
    @Transactional
    public RepResponseDTO updateRep(Long repId, RepRequestDTO repRequest) {

        Reps existingRep = repsRepository.findById(repId)
                .orElseThrow(() -> new RuntimeException(
                        "Rep not found with id: " + repId));

        // Update only allowed fields
        existingRep.setRepName(repRequest.getRepName());
        existingRep.setRepLocation(repRequest.getRepLocation());

        Reps updatedRep = repsRepository.save(existingRep);

        return new RepResponseDTO(
                updatedRep.getRepId(),
                updatedRep.getRepName(),
                updatedRep.getRepLocation(),
                updatedRep.getUser().getUsername());
    }

    @Override
    @Transactional
    public void deleteRepById(Long repId) {

        Reps rep = repsRepository.findById(repId)
                .orElseThrow(() -> new RuntimeException(
                        "Rep not found with id: " + repId));

        // Delete rep first (because it owns the FK)
        repsRepository.delete(rep);

        // Then delete linked user
        userRepository.delete(rep.getUser());
    }

}
