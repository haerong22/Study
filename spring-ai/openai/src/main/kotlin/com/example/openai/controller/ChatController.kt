package com.example.openai.controller

import com.example.openai.service.ChatService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatController(
    private val chatService: ChatService,
) {

    @GetMapping("/chat")
    fun chat(
        @RequestParam("message") message: String,
    ): String? {
        return chatService.chat(message)
    }

    @GetMapping("/chat-message")
    fun chatMessage(
        @RequestParam("message") message: String,
    ): String? {
        return chatService.chatMessage(message)
    }

    @GetMapping("/chat/placeholder")
    fun chatPlaceholder(
        @RequestParam subject: String,
        @RequestParam tone: String,
        @RequestParam message: String,
    ): String? {
        return chatService.chatPlaceholder(subject, tone, message)
    }

}