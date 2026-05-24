package com.harshi_solution.warehouse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            var requestAttributes = org.springframework.web.context.request.RequestContextHolder.getRequestAttributes();

            if (requestAttributes instanceof org.springframework.web.context.request.ServletRequestAttributes attrs) {
                String authHeader = attrs.getRequest().getHeader("Authorization");

                if (authHeader != null) {
                    requestTemplate.header("Authorization", authHeader);
                }
            }
        };
    }
}
