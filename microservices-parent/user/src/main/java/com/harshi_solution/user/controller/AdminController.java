package com.harshi_solution.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harshi_solution.auth.dto.BaseUIResponse;
import com.harshi_solution.user.dto.RegisterRepsRequest;
import com.harshi_solution.user.dto.RegisterViewerRequest;
import com.harshi_solution.user.dto.UserResponseDTO;
import com.harshi_solution.user.entities.Role;
import com.harshi_solution.user.service.UserRegisterEntityService;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "delete Viewer using Id", description = "used to delete Viewer using Id")
    @DeleteMapping("/{id}")
    public BaseUIResponse<String> deleteViewer(
            @PathVariable @NonNull Long id) {

        userRegisterEntityService.deleteViewerById(id);

        BaseUIResponse<String> response = new BaseUIResponse<>();
        response.setResponsePayload(
                "Viewer with ID " + id + " deleted successfully.");

        return response;
    }
}
