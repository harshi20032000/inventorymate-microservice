package com.harshi_solution.audit_trail.config;


import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
    basePackages         = "com.harshi_solution.audit_trail.postgres.repository",
    entityManagerFactoryRef = "postgresEntityManagerFactory",
    transactionManagerRef   = "postgresTransactionManager"
)
public class PostgresConfig {

    // reads spring.datasource.* from application.properties
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties postgresDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource postgresDataSource() {
        return postgresDataSourceProperties()
            .initializeDataSourceBuilder()
            .build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em =
            new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(postgresDataSource());
        em.setPackagesToScan("com.harshi_solution.audit_trail.postgres.entity");

        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setGenerateDdl(true);
        em.setJpaVendorAdapter(adapter);

        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.hbm2ddl.auto",              "update");
        props.put("hibernate.dialect",
                  "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.show_sql",                  "false");
        props.put("hibernate.format_sql",                "true");
        em.setJpaPropertyMap(props);

        return em;
    }

    @Bean
    @Primary
    public PlatformTransactionManager postgresTransactionManager(
            EntityManagerFactory postgresEntityManagerFactory) {
        return new JpaTransactionManager(postgresEntityManagerFactory);
    }
}