package com.example.webflux.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveRedisTemplate;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RedisConfig implements ApplicationListener<ApplicationReadyEvent> {

    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        reactiveRedisTemplate.opsForValue().get("1")
                .doOnSuccess(i -> log.info("Initialize to redis connection"))
                .doOnError((err) -> {
                    log.error("Failed to initialize redis connection: {}", err.getMessage());
                    SpringApplication.exit(event.getApplicationContext(), () -> -110);
                })
                .subscribe();
    }

}