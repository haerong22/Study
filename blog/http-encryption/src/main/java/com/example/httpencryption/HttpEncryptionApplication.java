package com.example.httpencryption;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan(basePackages = {"com.example.httpencryption.common"})
@SpringBootApplication
public class HttpEncryptionApplication {

    public static void main(String[] args) {
        SpringApplication.run(HttpEncryptionApplication.class, args);
    }

}
