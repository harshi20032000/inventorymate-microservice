package com.harshi_solution.auth.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.harshi_solution.auth.audit.AuditFilter;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AuditFilter> auditFilterRegistration(
            AuditFilter filter) {
        FilterRegistrationBean<AuditFilter> reg = new FilterRegistrationBean<>(filter);
        reg.addUrlPatterns("/api/*");
        reg.setOrder(1);
        return reg;
    }
}
