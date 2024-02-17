package org.example.delivery.storeadmin.config.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "org.example.delivery.db")
@EnableJpaRepositories(basePackages = "org.example.delivery.db")
public class JpaConfig {
}
