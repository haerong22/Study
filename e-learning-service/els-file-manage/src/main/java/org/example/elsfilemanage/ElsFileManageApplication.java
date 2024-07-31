package org.example.elsfilemanage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ElsFileManageApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElsFileManageApplication.class, args);
	}

}
