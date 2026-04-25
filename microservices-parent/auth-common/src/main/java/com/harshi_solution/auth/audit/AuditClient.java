package com.harshi_solution.auth.audit;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// "audit-trail" must match spring.application.name in audit-trail service
@FeignClient(name = "audit-trail", fallback = AuditClientFallback.class)
public interface AuditClient {

    @PostMapping("/api/v1/audit/record")
    void record(@RequestBody AuditEventDTO event);
}