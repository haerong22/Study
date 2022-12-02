package com.example.sns.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class EmitterRepository {

    private final Map<String, SseEmitter> emitterMap = new HashMap<>();

    public SseEmitter save(Integer userId, SseEmitter sseEmitter) {
        final String key = getKey(userId);
        emitterMap.put(key, sseEmitter);
        log.info("Set sseEmitter for {}", userId);
        return sseEmitter;
    }

    public Optional<SseEmitter> get(Integer userId) {
        final String key = getKey(userId);
        SseEmitter sseEmitter = emitterMap.get(key);
        log.info("Get sseEmitter for {}", userId);
        return Optional.ofNullable(sseEmitter);
    }

    public void delete(Integer userid) {
        emitterMap.remove(getKey(userid));
    }

    private String getKey(Integer userId) {
        return "Emitter:UID:" + userId;
    }
}
