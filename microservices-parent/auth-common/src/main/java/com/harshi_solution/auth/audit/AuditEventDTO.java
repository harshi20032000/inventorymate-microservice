package com.harshi_solution.auth.audit;

import java.time.LocalDateTime;

public class AuditEventDTO {

    private String businessCorrelationId;
    private String correlationId;
    private String referenceId;

    private String username;
    private String role;
    private String serviceName;

    private String method;
    private String url;

    private String boundType;

    private Integer responseStatus;
    private Long    durationMs;

    private String requestPayload;
    private String responsePayload;
    private String errorMessage;

    private String userAgent;

    private LocalDateTime timestamp;

    // ── getters / setters ────────────────────────────────
    public String getBusinessCorrelationId()                      { return businessCorrelationId; }
    public void   setBusinessCorrelationId(String v)              { this.businessCorrelationId = v; }
    public String getCorrelationId()                              { return correlationId; }
    public void   setCorrelationId(String v)                      { this.correlationId = v; }
    public String getReferenceId()                                { return referenceId; }
    public void   setReferenceId(String v)                        { this.referenceId = v; }
    public String getUsername()                                   { return username; }
    public void   setUsername(String v)                           { this.username = v; }
    public String getRole()                                       { return role; }
    public void   setRole(String v)                               { this.role = v; }
    public String getServiceName()                                { return serviceName; }
    public void   setServiceName(String v)                        { this.serviceName = v; }
    public String getMethod()                                     { return method; }
    public void   setMethod(String v)                             { this.method = v; }
    public String getUrl()                                        { return url; }
    public void   setUrl(String v)                                { this.url = v; }
    public String getBoundType()                                  { return boundType; }
    public void   setBoundType(String v)                          { this.boundType = v; }
    public Integer getResponseStatus()                            { return responseStatus; }
    public void    setResponseStatus(Integer v)                   { this.responseStatus = v; }
    public Long   getDurationMs()                                 { return durationMs; }
    public void   setDurationMs(Long v)                           { this.durationMs = v; }
    public String getRequestPayload()                             { return requestPayload; }
    public void   setRequestPayload(String v)                     { this.requestPayload = v; }
    public String getResponsePayload()                            { return responsePayload; }
    public void   setResponsePayload(String v)                    { this.responsePayload = v; }
    public String getErrorMessage()                               { return errorMessage; }
    public void   setErrorMessage(String v)                       { this.errorMessage = v; }
    public String getUserAgent()                                  { return userAgent; }
    public void   setUserAgent(String v)                          { this.userAgent = v; }
    public LocalDateTime getTimestamp()                           { return timestamp; }
    public void          setTimestamp(LocalDateTime v)            { this.timestamp = v; }
}