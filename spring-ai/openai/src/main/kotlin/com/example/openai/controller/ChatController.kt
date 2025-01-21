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
}