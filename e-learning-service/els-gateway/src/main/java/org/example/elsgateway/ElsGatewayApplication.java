package org.example.elsgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ElsGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElsGatewayApplication.class, args);
	}

}
