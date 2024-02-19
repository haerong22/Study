package com.example.effectivejava.chapter01.item01;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public HelloService helloService() {
        return new ChineseHelloService();
    }
}
