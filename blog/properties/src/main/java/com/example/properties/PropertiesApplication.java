package com.example.properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
//@PropertySource(value = {"test.properties", "test2.properties"})
@PropertySource(value = {"test.yml", "test2.yml"}, factory = YamlPropertySourceFactory.class)
public class PropertiesApplication {

    public static void main(String[] args) {
        SpringApplication.run(PropertiesApplication.class, args);
    }
}
