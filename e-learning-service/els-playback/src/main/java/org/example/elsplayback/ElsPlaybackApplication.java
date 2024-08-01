package org.example.elsplayback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ElsPlaybackApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElsPlaybackApplication.class, args);
	}

}
