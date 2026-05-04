package com.harshi_solution.audit_trail.mongo.document;


import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "audit_logs")
public class AuditLogDocument {

    @Id
    private String  id;

    @Indexed
    private String  username;

    @Indexed
    private String  role;

    @Indexed
    private String  serviceName;

    private String  method;
    private String  endpoint;
    private Integer responseStatus;
    private Long    durationMs;

    @Indexed(expireAfterSeconds = 7776000) // TTL — auto-delete after 90 days
    private LocalDateTime timestamp;

    private String  userAgent;
    private boolean isError;     // true if responseStatus >= 400

    // ── getters / setters ────────────────────────────────
    public String  getId()                   { return id; }
    public String  getUsername()             { return username; }
    public void    setUsername(String u)     { this.username = u; }
    public String  getRole()                 { return role; }
    public void    setRole(String r)         { this.role = r; }
    public String  getServiceName()          { return serviceName; }
    public void    setServiceName(String s)  { this.serviceName = s; }
    public String  getMethod()               { return method; }
    public void    setMethod(String m)       { this.method = m; }
    public String  getEndpoint()             { return endpoint; }
    public void    setEndpoint(String e)     { this.endpoint = e; }
    public Integer getResponseStatus()       { return responseStatus; }
    public void    setResponseStatus(Integer rs) { this.responseStatus = rs; }
    public Long    getDurationMs()           { return durationMs; }
    public void    setDurationMs(Long d)     { this.durationMs = d; }
    public LocalDateTime getTimestamp()            { return timestamp; }
    public void    setTimestamp(LocalDateTime t)   { this.timestamp = t; }
    public String  getUserAgent()            { return userAgent; }
    public void    setUserAgent(String ua)   { this.userAgent = ua; }
    public boolean isError()                 { return isError; }
    public void    setError(boolean e)       { this.isError = e; }
}
