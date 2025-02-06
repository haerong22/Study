package com.example.openai.tts.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class RouteController {

    @GetMapping("/tts")
    fun audioPlay(): String {
        return "tts"
    }
}