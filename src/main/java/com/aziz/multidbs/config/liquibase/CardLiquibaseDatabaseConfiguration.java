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
public class CardLiquibaseDatabaseConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.card.liquibase")
    DataSourceProperties liquibaseCardDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.card.liquibase.hikari")
    DataSource liquibaseCardDataSource(@Qualifier("liquibaseCardDataSourceProperties") DataSourceProperties liquibaseCardDataSourceProperties) {
        return liquibaseCardDataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public SpringLiquibase cardLiquibase(@Qualifier("liquibaseCardDataSource") DataSource liquibaseCardDataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:db/creditcard/changelog/db.changelog-root.xml");
        liquibase.setDataSource(liquibaseCardDataSource);
        return liquibase;
    }
}
