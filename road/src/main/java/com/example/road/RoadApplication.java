package com.example.road;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RoadApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoadApplication.class, args);
    }

}
