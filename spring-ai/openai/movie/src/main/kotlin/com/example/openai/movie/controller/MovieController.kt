package com.example.openai.movie.controller

import com.example.openai.movie.service.MovieService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam


@Controller
class MovieController(
    private val movieService: MovieService,
) {

    @PostMapping("/recommend")
    fun recommendMovies1(
        @RequestParam("query") query: String,
        model: Model
    ): String {
        val result = movieService.recommend(query)

        if (result != null) {
            model.addAttribute("title", result.title)
            model.addAttribute("results", result.content)
            model.addAttribute("youtubeUrls", result.youtubeUrls)
        } else {
            model.addAttribute("message", "No closely related movies found.")
        }

        model.addAttribute("query", query)
        return "movie"
    }

}