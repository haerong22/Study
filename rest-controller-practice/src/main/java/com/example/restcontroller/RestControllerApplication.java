package com.example.restcontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class RestControllerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestControllerApplication.class, args);
    }

}
