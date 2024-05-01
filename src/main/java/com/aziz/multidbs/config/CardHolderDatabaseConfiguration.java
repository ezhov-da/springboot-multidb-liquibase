package com.aziz.multidbs.config;

import com.aziz.multidbs.domain.cardholder.CreditCardHolder;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "com.aziz.multidbs.repository.cardholder",
        entityManagerFactoryRef = "cardHolderEntityManagerFactory",
        transactionManagerRef = "cardHolderPlatformTxManager")
public class CardHolderDatabaseConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.cardholder.datasource")
    DataSourceProperties cardHolderDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.cardholder.datasource.hikari")
    DataSource cardHolderDataSource(@Qualifier("cardHolderDataSourceProperties") DataSourceProperties cardHolderDataSourceProperties) {
        return cardHolderDataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    LocalContainerEntityManagerFactoryBean cardHolderEntityManagerFactory(@Qualifier("cardHolderDataSource") DataSource cardHolderDataSource,
                                                                   EntityManagerFactoryBuilder builder) {
        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", "validate");
        LocalContainerEntityManagerFactoryBean emf =  builder
                .dataSource(cardHolderDataSource)
                .packages(CreditCardHolder.class)
                .persistenceUnit("cardholder")
                .build();
        emf.setJpaProperties(properties);
        return emf;
    }

    @Bean
    PlatformTransactionManager cardHolderPlatformTxManager(@Qualifier("cardHolderEntityManagerFactory") LocalContainerEntityManagerFactoryBean cardHolderEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(cardHolderEntityManagerFactory.getObject()));
    }
}
