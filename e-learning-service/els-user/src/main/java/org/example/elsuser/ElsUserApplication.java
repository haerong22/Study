package org.example.elsuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ElsUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElsUserApplication.class, args);
	}

}
