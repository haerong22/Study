package com.example.openai.movie.service

data class MovieRecommend(
    val title: String,
    val content: String,
    val youtubeUrls: List<String>,
)
