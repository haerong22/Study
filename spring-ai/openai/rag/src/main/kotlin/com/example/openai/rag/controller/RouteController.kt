package com.example.openai.rag.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class RouteController {

    @GetMapping("/pdf-rag")
    fun korea(): String {
        return "rag"
    }
}