package com.example.openai.recipe.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class RouteController {

    @GetMapping("/recipe-view")
    fun recipeView(): String {
        return "recipe"
    }
}