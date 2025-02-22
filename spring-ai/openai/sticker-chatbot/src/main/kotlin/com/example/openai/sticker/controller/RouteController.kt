package com.example.openai.sticker.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class RouteController {

    @GetMapping("/chat")
    fun chat(): String {
        return "sticker"
    }
}