package com.harshi_solution.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harshi_solution.user.dto.AuthResponseDTO;
import com.harshi_solution.user.dto.BaseUIResponse;
import com.harshi_solution.user.dto.ChangePasswordRequest;
import com.harshi_solution.user.dto.LoginRequest;
import com.harshi_solution.user.service.AuthService;


@RestController
@RequestMapping("/api/v1/user")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public BaseUIResponse<AuthResponseDTO> login(
            @RequestBody LoginRequest request) {
        BaseUIResponse<AuthResponseDTO> response = new BaseUIResponse<>();

        response.setResponsePayload(authService.login(request));

        return response;
    }

    @PostMapping("/change-password")
    public BaseUIResponse<?> changePassword(
            @RequestBody ChangePasswordRequest request) {

        authService.changePassword(request);
        BaseUIResponse<String> response = new BaseUIResponse<>();
        response.setResponsePayload("Password changed successfully");
        return response;
    }
    @GetMapping("/log-out")
    public BaseUIResponse<Void> logout() {

        BaseUIResponse<Void> response = new BaseUIResponse<>();

        response.setEmptyResponsePayload();

        return response;
    }

    @PostMapping("/refresh-token")
    public BaseUIResponse<AuthResponseDTO> refreshToken(
           @RequestBody AuthResponseDTO request) {

        BaseUIResponse<AuthResponseDTO> response = new BaseUIResponse<>();

        response.setResponsePayload(authService.refreshToken(request));

        return response;
    }
}