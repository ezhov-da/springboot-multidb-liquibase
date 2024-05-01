package com.aziz.multidbs.config.liquibase;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class CardHolderLiquibaseDatabaseConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.cardholder.liquibase")
    DataSourceProperties liquibaseCardHolderDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.cardholder.liquibase.hikari")
    DataSource liquibaseCardHolderDataSource(@Qualifier("liquibaseCardHolderDataSourceProperties") DataSourceProperties liquibaseCardHolderDataSourceProperties) {
        return liquibaseCardHolderDataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public SpringLiquibase cardHolderLiquibase(@Qualifier("liquibaseCardHolderDataSource") DataSource liquibaseCardHolderDataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:db/pan/changelog/db.changelog-root.xml");
        liquibase.setDataSource(liquibaseCardHolderDataSource);
        return liquibase;
    }
}
