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
public class PANLiquibaseDatabaseConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.pan.liquibase")
    DataSourceProperties liquibasePANDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.pan.liquibase.hikari")
    DataSource liquibasePANDataSource(@Qualifier("liquibasePANDataSourceProperties") DataSourceProperties liquibasePANDataSourceProperties) {
        return liquibasePANDataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public SpringLiquibase PANLiquibase(@Qualifier("liquibasePANDataSource") DataSource liquibasePANDataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:db/pan/changelog/db.changelog-root.xml");
        liquibase.setDataSource(liquibasePANDataSource);
        return liquibase;
    }
}
