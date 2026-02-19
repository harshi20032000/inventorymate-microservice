package com.harshi_solution.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harshi_solution.user.dto.BaseUIResponse;
import com.harshi_solution.user.dto.RegisterRepsRequest;
import com.harshi_solution.user.dto.RegisterViewerRequest;
import com.harshi_solution.user.dto.UserResponseDTO;
import com.harshi_solution.user.entities.Role;
import com.harshi_solution.user.service.UserRegisterEntityService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    @Autowired
    private UserRegisterEntityService userRegisterEntityService;

    @PostMapping("/register-reps")
    public BaseUIResponse<String> registerReps(
            @Valid @RequestBody RegisterRepsRequest request) {

        userRegisterEntityService.registerReps(request);

        BaseUIResponse<String> response = new BaseUIResponse<>();
        response.setResponsePayload("Reps registered successfully");

        return response;
    }

    @PostMapping("/register-viewer")
    public BaseUIResponse<String> registerViewer(
            @Valid @RequestBody RegisterViewerRequest request) {

        userRegisterEntityService.registerViewer(request);

        BaseUIResponse<String> response = new BaseUIResponse<>();
        response.setResponsePayload("Viewer registered successfully");

        return response;
    }

    @GetMapping("/users")
    public BaseUIResponse<List<UserResponseDTO>> getUsersByRole(
            @RequestParam Role role) {

        List<UserResponseDTO> users = userRegisterEntityService.getUsersByRole(role);
        BaseUIResponse<List<UserResponseDTO>> response = new BaseUIResponse<>();
        response.setResponsePayload(users);
        return response;
    }
}
