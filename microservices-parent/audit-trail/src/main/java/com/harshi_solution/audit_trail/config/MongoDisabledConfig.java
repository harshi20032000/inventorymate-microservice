package com.harshi_solution.audit_trail.config;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
    name           = "audit.mongodb.enabled",
    havingValue    = "false",
    matchIfMissing = true   // ← if property missing, exclude Mongo
)
@EnableAutoConfiguration(exclude = {
    MongoAutoConfiguration.class,
    MongoDataAutoConfiguration.class
})
public class MongoDisabledConfig {
    // When audit.mongodb.enabled=false this class activates and
    // tells Spring Boot to not attempt any MongoDB auto-configuration
    // so it won't try to connect and won't fail at startup
}
