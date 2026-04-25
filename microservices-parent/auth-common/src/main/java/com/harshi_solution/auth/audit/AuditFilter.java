package com.harshi_solution.auth.audit;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuditFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(AuditFilter.class);

    private static final String SECRET_KEY = "your-secure-secret-key-min-32bytes";

    private static final SecretKey key = Keys.hmacShaKeyFor(
            SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    @Value("${spring.application.name:unknown}")
    private String serviceName;

    private final AuditClient auditClient;

    public AuditFilter(AuditClient auditClient) {
        this.auditClient = auditClient;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest  request,
                                    HttpServletResponse response,
                                    FilterChain         chain)
            throws ServletException, IOException {

        long start = System.currentTimeMillis();

        try {
            chain.doFilter(request, response);
        } finally {
            String uri = request.getRequestURI();

            // skip actuator and health endpoints
            if (uri.contains("/actuator") || uri.contains("/health")) return;

            long   duration = System.currentTimeMillis() - start;
            String username = "anonymous";
            String role     = "unknown";

            // decode JWT if present
            String token = request.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                try {
                    Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
                    username = claims.getSubject();
                    role     = claims.get("role", String.class);
                } catch (Exception ignored) {
                    // invalid or expired token — log as anonymous
                }
            }

            AuditEventDTO event = new AuditEventDTO();
            event.setUsername(username);
            event.setRole(role);
            event.setServiceName(serviceName);
            event.setMethod(request.getMethod());
            event.setEndpoint(uri);
            event.setResponseStatus(response.getStatus());
            event.setDurationMs(duration);
            event.setUserAgent(request.getHeader("User-Agent"));
            event.setTimestamp(Instant.now());

            // fire-and-forget in virtual thread — never blocks the response
            Thread.ofVirtual().start(() -> {
                try {
                    auditClient.record(event);
                } catch (Exception e) {
                    log.warn("AuditFilter: failed to send audit event — {}", e.getMessage());
                }
            });

            log.info("[AUDIT] {} {} {} {} {}ms {}",
                username, role, request.getMethod(),
                uri, duration, response.getStatus());
        }
    }
}
