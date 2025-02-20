package com.example.openai.hotel.controller

import com.example.openai.hotel.service.HotelService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class HotelController(
    private val hotelService: HotelService,
) {

    @GetMapping("/question")
    fun chatbot(
        @RequestParam("question") question: String,
    ): Flux<String> {
        return hotelService.chat(question)
    }
}