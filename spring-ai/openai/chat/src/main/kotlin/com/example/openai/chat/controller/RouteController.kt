package com.example.openai.chat.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class RouteController {

    @GetMapping("/ask-view")
    fun askView(): String {
        return "ask"
    }
}