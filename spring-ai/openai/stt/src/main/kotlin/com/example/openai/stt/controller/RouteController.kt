package com.example.openai.stt.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping



@Controller
class RouteController {

    @GetMapping("/stt")
    fun stt(): String {
        return "stt"
    }
}