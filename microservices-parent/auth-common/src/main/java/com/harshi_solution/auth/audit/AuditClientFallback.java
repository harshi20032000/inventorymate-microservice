package com.harshi_solution.auth.audit;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AuditClientFallback implements AuditClient {

    private static final Logger log = LoggerFactory.getLogger(AuditClientFallback.class);

    @Override
    public void record(AuditEventDTO event) {
        // audit-trail service is down — log locally and move on
        // never throw — never affect the actual service request
        log.warn("AuditClientFallback: audit-trail unreachable, dropping event for {} {}",
            event.getUsername(), event.getEndpoint());
    }
}
