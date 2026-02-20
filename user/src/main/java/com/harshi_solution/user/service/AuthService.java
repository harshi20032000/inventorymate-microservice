package com.harshi_solution.user.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harshi_solution.user.dto.ChangePasswordRequest;
import com.harshi_solution.user.dto.LoginRequest;
import com.harshi_solution.user.entities.UserRegisterEntity;
import com.harshi_solution.user.repo.UserRegisterEntityRepository;
import com.harshi_solution.user.util.JWTUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

    public void login(LoginRequest request,
            HttpServletResponse response) {

        Authentication authResult = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        String username = authResult.getName();

        String accessToken = jwtUtil.generateAccessToken(username);

        String refreshToken = jwtUtil.generateRefreshToken(username);

        response.setHeader("Authorization",
                "Bearer " + accessToken);

        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);

        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(true);
        refreshCookie.setPath("/api/v1/user/refresh-token");
        refreshCookie.setMaxAge(7 * 24 * 60 * 60);

        response.addCookie(refreshCookie);
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

    public void logout(HttpServletResponse response) {

        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/api/v1/user/refresh-token");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }

    public void refreshToken(HttpServletRequest request,
            HttpServletResponse response) {

        String refreshToken = extractRefreshTokenFromCookies(request);

        if (refreshToken == null) {
            throw new RuntimeException("Refresh token missing");
        }

        String username = jwtUtil.validateAndExtractUsername(refreshToken);

        if (username == null) {
            throw new RuntimeException("Invalid refresh token");
        }

        String newAccessToken = jwtUtil.generateAccessToken(username);

        response.setHeader("Authorization",
                "Bearer " + newAccessToken);
    }

    private String extractRefreshTokenFromCookies(
            HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return null;

        for (Cookie cookie : cookies) {
            if ("refreshToken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

}