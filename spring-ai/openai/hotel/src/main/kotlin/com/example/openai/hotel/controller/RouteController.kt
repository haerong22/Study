package com.example.openai.hotel.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping



@Controller
class RouteController {

    @GetMapping("/hotel")
    fun hotel(): String {
        return "hotel"
    }
}