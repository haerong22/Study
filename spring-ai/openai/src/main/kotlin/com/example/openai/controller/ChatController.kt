package com.example.openai.controller

import com.example.openai.entity.Answer
import com.example.openai.entity.Movie
import com.example.openai.service.ChatService
import org.springframework.ai.chat.model.ChatResponse
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

    @GetMapping("/chat-json")
    fun chatJson(
        @RequestParam message: String,
    ): ChatResponse? {
        return chatService.chatJson(message)
    }

    @GetMapping("/chat-object")
    fun chatObject(
        @RequestParam message: String,
    ): Answer? {
        return chatService.chatObject(message)
    }



    @GetMapping("/recipe")
    fun recipe(
        @RequestParam foodName: String,
        @RequestParam question: String,
    ): Answer? {
        return chatService.recipe(foodName, question)
    }

    @GetMapping("/chat-list")
    fun chatList(
        @RequestParam message: String,
    ): List<String>? {
        return chatService.chatList(message)
    }

    @GetMapping("/chat-map")
    fun chatMap(
        @RequestParam message: String,
    ): Map<String, String>? {
        return chatService.chatMap(message)
    }

    @GetMapping("/chat/movie")
    fun chatMovie(
        @RequestParam directorName: String,
    ): List<Movie>? {
        return chatService.chatMovie(directorName)
    }
}