package com.example.chatting.controller;

import com.example.chatting.domain.dto.ChatMessage;
import com.example.chatting.redis.RedisPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final RedisPublisher redisPublisher;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @MessageMapping("/message")
    public void message(Principal principal, ChatMessage chatMessage) throws JsonProcessingException {
        System.out.println("controller");
        chatMessage.setSessionId(principal.getName());
        redisPublisher.publish("message", chatMessage);
    }

    @ResponseBody
    @GetMapping("/test")
    public void test() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSessionId("session");
        chatMessage.setMessage("hello");

        ChatMessage chatMessage2 = new ChatMessage();
        chatMessage2.setSessionId("session");
        chatMessage2.setMessage("hello");
        chatMessage2.setDate(LocalDateTime.now());

        chatMessage2.setTestEnum(ChatMessage.TestEnum.A);

        List<ChatMessage> testList = new ArrayList<>();

        testList.add(chatMessage);
        testList.add(chatMessage2);

        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set("test", testList);

        List<ChatMessage> test = (List<ChatMessage>) ops.get("test");
        System.out.println("test = " + test);

//        redisPublisher.publish("message", chatMessage);
    }
}
