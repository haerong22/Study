package com.example.openai.image.controller

import com.example.openai.image.entity.ImageRequestDTO
import com.example.openai.image.service.ImageService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ImageGenerationController(
    private val imageService: ImageService,
) {

    @PostMapping(value = ["/image"], consumes = ["application/json; charset=UTF-8"])
    fun imageGen(
        @RequestBody request: ImageRequestDTO,
    ): List<String> {
        return imageService.getImageGen(request)
    }
}