package com.harshi_solution.audit_trail.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableMongoRepositories(
    basePackages = "com.harshi_solution.audit_trail.mongo.repository"
)
@ConditionalOnProperty(
    name    = "audit.mongodb.enabled",
    havingValue = "true",
    matchIfMissing = false 
)

public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Override
    protected String getDatabaseName() {
        return "audit_logs_db";
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(mongoUri);
    }

    // auto-index creation (for TTL index on AuditLogDocument)
    @Override
    protected boolean autoIndexCreation() {
        return true;
    }

    // optional — enables MongoDB transactions if you need them later
    @Bean
    public MongoTransactionManager mongoTransactionManager(
            MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }
}
