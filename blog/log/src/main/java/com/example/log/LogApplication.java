package com.example.log;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class LogApplication {

    @Value("${common}")
    private String common;

    @Value("${test}")
    private String test;

    public static void main(String[] args) {
        SpringApplication.run(LogApplication.class, args);
    }

    @PostConstruct
    private void start() {
        System.out.println("common = " + common);
        System.out.println("test = " + test);
    }
}
