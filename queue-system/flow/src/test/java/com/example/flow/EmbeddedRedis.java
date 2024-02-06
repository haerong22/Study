package com.example.flow;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.core.io.ClassPathResource;
import redis.embedded.RedisServer;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@TestConfiguration
public class EmbeddedRedis {

    private final RedisServer redisServer;

    public EmbeddedRedis(@Value("${spring.data.redis.port}") int port) throws IOException {
        if (isArmMac()) {
            this.redisServer = new RedisServer(port, getRedisFileForArcMac());
        } else {
            this.redisServer = new RedisServer(port);
        }
    }

    private File getRedisFileForArcMac() throws IOException {
        return new ClassPathResource("redis/redis-server-7.2.3-mac-arm64").getFile();
    }

    @PostConstruct
    public void start() throws IOException {
        redisServer.start();
    }

    private boolean isArmMac() {
        return Objects.equals(System.getProperty("os.arch"), "aarch64")
                && Objects.equals(System.getProperty("os.name"), "Mac OS X");
    }

    @PreDestroy
    public void stop() throws IOException {
        this.redisServer.stop();
    }
}
