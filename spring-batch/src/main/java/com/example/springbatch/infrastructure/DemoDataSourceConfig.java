package com.example.springbatch.infrastructure;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class DemoDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari.demo")
    public DataSource demoDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    public PlatformTransactionManager demoTransactionManager(
            @Qualifier("demoEntityManagerFactory") EntityManagerFactory demoEntityManagerFactory) {
        JpaTransactionManager demoTransactionManager = new JpaTransactionManager();
        demoTransactionManager.setEntityManagerFactory(demoEntityManagerFactory);
        return demoTransactionManager;
    }
}
