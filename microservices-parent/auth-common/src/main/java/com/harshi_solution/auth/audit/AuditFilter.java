package com.harshi_solution.auth.audit;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

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

    // header name for passing correlation ID between services
    public static final String CORRELATION_HEADER         = "X-Correlation-Id";
    public static final String BUSINESS_CORRELATION_HEADER = "X-Business-Correlation-Id";

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

        // skip health and actuator endpoints
        String uri = request.getRequestURI();
        if (uri.contains("/actuator") || uri.contains("/health")) {
            chain.doFilter(request, response);
            return;
        }

        // wrap request and response to allow body re-reading
        ContentCachingRequestWrapper  wrappedRequest  = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        LocalDateTime timestamp = LocalDateTime.now();
        long start = System.currentTimeMillis();

        // generate or propagate correlation ID
        String correlationId = request.getHeader(CORRELATION_HEADER);
        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
        }
        String businessCorrelationId = request.getHeader(BUSINESS_CORRELATION_HEADER);

        // set correlation ID on response so downstream can read it
        wrappedResponse.setHeader(CORRELATION_HEADER, correlationId);

        try {
            chain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            long duration = System.currentTimeMillis() - start;

            String username = "anonymous";
            String role     = "unknown";

            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                try {
                    Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(authHeader)
                    .getPayload();
                    username = claims.getSubject();
                    role     = claims.get("role", String.class);
                } catch (Exception ignored) {}
            }

            // read body bytes after filter chain completed
            String requestBody  = readBody(wrappedRequest.getContentAsByteArray());
            String responseBody = readBody(wrappedResponse.getContentAsByteArray());
            int    status       = wrappedResponse.getStatus();

            // copy response body back to original response
            wrappedResponse.copyBodyToResponse();;

            // build event
            final String  finalUsername    = username;
            final String  finalRole        = role;
            final String  finalCorrId      = correlationId;
            final String  finalBizCorrId   = businessCorrelationId;

            AuditEventDTO event = new AuditEventDTO();
            event.setCorrelationId(finalCorrId);
            event.setBusinessCorrelationId(finalBizCorrId);
            event.setUsername(finalUsername);
            event.setRole(finalRole);
            event.setServiceName(serviceName);
            event.setMethod(request.getMethod());
            event.setUrl(uri);
            event.setBoundType("INBOUND");
            event.setResponseStatus(status);
            event.setDurationMs(duration);
            event.setRequestPayload(truncate(requestBody,  1000));
            event.setResponsePayload(truncate(responseBody, 1000));
            event.setErrorMessage(status >= 400 ? extractError(responseBody) : null);
            event.setUserAgent(request.getHeader("User-Agent"));
            event.setTimestamp(timestamp);

            log.info("[AUDIT] {} {} {} {} {}ms {}",
                finalUsername, finalRole,
                request.getMethod(), uri, duration, status);

            // fire-and-forget in virtual thread
            Thread.ofVirtual().start(() -> {
                try {
                    auditClient.record(event);
                } catch (Exception e) {
                    log.warn("AuditFilter: failed to send event — {}", e.getMessage());
                }
            });
        }
    }

    private String readBody(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null;
        try {
            return new String(bytes, "UTF-8");
        } catch (Exception e) {
            return null;
        }
    }

    private String truncate(String s, int max) {
        if (s == null) return null;
        return s.length() <= max ? s : s.substring(0, max) + "…";
    }

    private String extractError(String responseBody) {
        if (responseBody == null) return null;
        // try to extract message from BaseUIResponse JSON
        try {
            if (responseBody.contains("\"message\"")) {
                int start = responseBody.indexOf("\"message\"") + 11;
                int end   = responseBody.indexOf("\"", start);
                return responseBody.substring(start, end);
            }
        } catch (Exception ignored) {}
        return null;
    }
}