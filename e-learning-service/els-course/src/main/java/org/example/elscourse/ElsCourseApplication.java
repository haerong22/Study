package org.example.elscourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ElsCourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElsCourseApplication.class, args);
	}

}
