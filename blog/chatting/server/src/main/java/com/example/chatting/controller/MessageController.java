package com.example.chatting.controller;

import com.example.chatting.redis.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final RedisPublisher redisPublisher;

    @MessageMapping("/message")
    public void message(String message) {
        redisPublisher.publish("message", message);
    }
}
