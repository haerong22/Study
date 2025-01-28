package com.example.openai.chat.controller

import com.example.openai.chat.service.ChatService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AskController(
    private val chatService: ChatService,
) {

    @GetMapping("/ask")
    fun getResponse(
        @RequestParam message: String,
    ): String {
        return chatService.getResponse(message)
    }

    @GetMapping("/ask-ai")
    fun getResponseOptions(
        @RequestParam message: String,
    ): String {
        return chatService.getResponseOptions(message)
    }
}