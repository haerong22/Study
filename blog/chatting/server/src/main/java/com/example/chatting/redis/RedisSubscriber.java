package com.example.chatting.redis;

import com.example.chatting.domain.dto.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    public void sendMessage(String message) {
        try {
            ChatMessage chatMessage = objectMapper.readValue(message, ChatMessage.class);
            messagingTemplate.convertAndSend("/sub/message", chatMessage);
//            messagingTemplate.convertAndSendToUser(chatMessage.getSessionId(), "/sub/message", chatMessage);
        } catch (Exception e) {
            log.error("Subscriber Error", e);
        }
    }
}
