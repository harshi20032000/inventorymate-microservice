package com.harshi_solution.auth.config;


import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@ComponentScan(basePackages = "com.harshi_solution.auth")
@EnableFeignClients(basePackages = "com.harshi_solution.auth.audit")
public class AuthCommonAutoConfiguration {
    // Spring Boot will auto-detect this class via spring.factories
    // and apply it to any service that imports auth-common
}
