package com.example.chatting.redis;

import com.example.chatting.domain.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(String topic, ChatMessage message) {
        redisTemplate.convertAndSend(topic, message);
    }
}
