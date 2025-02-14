package com.example.openai.movie.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class RouteController {

    @GetMapping("/movie")
    fun getRecommendationForm(): String {
        return "movie"
    }
}