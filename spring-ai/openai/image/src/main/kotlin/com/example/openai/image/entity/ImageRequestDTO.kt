package com.example.openai.image.entity

data class ImageRequestDTO(
    val message: String,
    val model: String,
    val n: Int,
)
