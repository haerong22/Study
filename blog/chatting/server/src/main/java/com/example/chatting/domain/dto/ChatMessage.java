package com.example.chatting.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessage {

    public enum TestEnum {
        A, B, C
    }

    private String sessionId;
    private String message;
    private LocalDateTime date;
    private TestEnum testEnum;
}
