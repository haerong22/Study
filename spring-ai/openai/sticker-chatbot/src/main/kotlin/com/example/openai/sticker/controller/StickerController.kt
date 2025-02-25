package com.example.openai.sticker.controller

import com.example.openai.sticker.service.ChatResponse
import com.example.openai.sticker.service.StickerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class StickerController(
    private val stickerService: StickerService,
) {

    @GetMapping("/question")
    fun chatbot(
        @RequestParam("question") question: String,
    ): ChatResponse {
        return stickerService.chat(question)
    }
}