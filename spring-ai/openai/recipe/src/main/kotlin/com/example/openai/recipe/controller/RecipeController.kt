package com.example.openai.recipe.controller

import com.example.openai.recipe.entity.Recipe
import com.example.openai.recipe.service.RecipeService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class RecipeController(
    private val recipeService: RecipeService,
) {

    @PostMapping("/recipe")
    fun recipe(@RequestBody recipe: Recipe): Map<String, Any> {
        return recipeService.createRecipeWithUrls(recipe)
    }
}