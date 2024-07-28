package org.example.elsdiscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ElsDiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElsDiscoveryApplication.class, args);
    }

}
