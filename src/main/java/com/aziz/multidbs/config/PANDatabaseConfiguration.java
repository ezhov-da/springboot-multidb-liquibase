package com.aziz.multidbs.config;

import com.aziz.multidbs.domain.pan.CreditCardPAN;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "com.aziz.multidbs.repository.pan",
        entityManagerFactoryRef = "PANEntityManagerFactory",
        transactionManagerRef = "PANPlatformTxManager")
public class PANDatabaseConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.pan.datasource")
    @Primary
    DataSourceProperties PANDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.pan.datasource.hikari")
    DataSource PANDataSource(@Qualifier("PANDataSourceProperties") DataSourceProperties PANDataSourceProperties) {
        return PANDataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @Primary
    LocalContainerEntityManagerFactoryBean PANEntityManagerFactory(@Qualifier("PANDataSource") DataSource PANDataSource,
                                                                    EntityManagerFactoryBuilder builder) {
        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", "validate");
        properties.put("hibernate.physical_naming_strategy",
                "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
        LocalContainerEntityManagerFactoryBean emf = builder
                .dataSource(PANDataSource)
                .packages(CreditCardPAN.class)
                .persistenceUnit("pan")
                .build();
        emf.setJpaProperties(properties);
        return emf;
    }

    @Bean
    @Primary
    PlatformTransactionManager PANPlatformTxManager(@Qualifier("PANEntityManagerFactory") LocalContainerEntityManagerFactoryBean PANEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(PANEntityManagerFactory.getObject()));
    }
}
