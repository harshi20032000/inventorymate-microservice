package com.harshi_solution.auth;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {

    private static final String SECRET_KEY = "your-secure-secret-key-min-32bytes";

    private static final SecretKey key = Keys.hmacShaKeyFor(
            SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    private static final long ACCESS_EXPIRY_MIN = 15;
    private static final long REFRESH_EXPIRY_MIN = 7 * 24 * 60;

    public String generateAccessToken(String username, String role) {
        return generateToken(username, role, "access", ACCESS_EXPIRY_MIN);
    }

    public String generateRefreshToken(String username, String role) {
        return generateToken(username, role, "refresh", REFRESH_EXPIRY_MIN);
    }

    private String generateToken(String username, String role, String type, long expiryMinutes) {
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .claim("type", type)
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis()
                                + expiryMinutes * 60 * 1000))
                .signWith(key)
                .compact();
    }

    public String validateAndExtractUsername(String token) {
        try {
            return Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (JwtException e) {
            return null;
        }
    }

    public String extractRole(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get("role", String.class);
        } catch (JwtException e) {
            return null;
        }
    }

    public String extractTokenType(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get("type", String.class);
        } catch (JwtException e) {
            return null;
        }
    }
}