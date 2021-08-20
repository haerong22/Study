package com.example.redisserver;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RemoteService implements RemoteServiceInterface {

    @Override
    public void sayHello(String name) {

        String url = "http://localhost:8080/hi/" + name;
        RestTemplate restTemplate = new RestTemplate();

        String forObject = restTemplate.getForObject(url, String.class);
        System.out.println("forObject = " + forObject);
    }
}
