package com.harshi_solution.user.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harshi_solution.user.dto.AuthResponseDTO;
import com.harshi_solution.user.dto.ChangePasswordRequest;
import com.harshi_solution.user.dto.LoginRequest;
import com.harshi_solution.user.entities.UserRegisterEntity;
import com.harshi_solution.user.repo.UserRegisterEntityRepository;
import com.harshi_solution.user.util.JWTUtil;

@Service
@Transactional
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final UserRegisterEntityRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager,
            JWTUtil jwtUtil,
            UserRegisterEntityRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponseDTO login(LoginRequest request) {

        Authentication authResult = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        String username = authResult.getName();

        String accessToken = jwtUtil.generateAccessToken(username);
        String refreshToken = jwtUtil.generateRefreshToken(username);

        return new AuthResponseDTO(accessToken, refreshToken);

    }

    public void changePassword(ChangePasswordRequest request) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        UserRegisterEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(
                request.getOldPassword(),
                user.getPassword())) {

            throw new RuntimeException("Old password incorrect");
        }

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()));

        userRepository.save(user);
    }

    public AuthResponseDTO refreshToken(
            AuthResponseDTO request) {

        String username = jwtUtil.validateAndExtractUsername(
                request.getRefreshToken());

        if (username == null) {
            throw new RuntimeException("Invalid refresh token");
        }

        String newAccessToken = jwtUtil.generateAccessToken(username);

        String newRefreshToken = jwtUtil.generateRefreshToken(username);

        return new AuthResponseDTO(newAccessToken, newRefreshToken);

    }

}