package com.example.openai.math.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class RouteController {

    @GetMapping("/image-to-text")
    fun imageview(): String {
        return "image-to-text"
    }
}