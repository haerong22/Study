package com.example.remoteserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.remoteserver","com.example.remoteinterface" })
public class RemoteServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RemoteServerApplication.class, args);
    }
}
