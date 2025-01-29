package com.example.openai.image.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class RouteController {

    @GetMapping("/image-view")
    fun imageview(): String {
        return "image"
    }
}