package com.harshi_solution.auth.audit;

import java.time.Instant;

public class AuditEventDTO {

    private String username;
    private String role;
    private String serviceName;
    private String method;
    private String endpoint;
    private Integer responseStatus;
    private Long durationMs;
    private String userAgent;
    private Instant timestamp;

    public String getUsername() {
        return username;
    }

    public void setUsername(String u) {
        this.username = u;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String r) {
        this.role = r;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String s) {
        this.serviceName = s;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String m) {
        this.method = m;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String e) {
        this.endpoint = e;
    }

    public Integer getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(Integer r) {
        this.responseStatus = r;
    }

    public Long getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(Long d) {
        this.durationMs = d;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String ua) {
        this.userAgent = ua;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant t) {
        this.timestamp = t;
    }
}
