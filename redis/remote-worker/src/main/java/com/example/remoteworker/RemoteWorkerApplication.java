package com.example.remoteworker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.remoteworker","com.example.remoteinterface" })
public class RemoteWorkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RemoteWorkerApplication.class, args);
    }
}
