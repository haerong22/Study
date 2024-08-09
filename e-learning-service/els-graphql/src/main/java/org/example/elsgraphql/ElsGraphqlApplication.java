package org.example.elsgraphql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
public class ElsGraphqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElsGraphqlApplication.class, args);
	}

}
