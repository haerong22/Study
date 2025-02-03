package com.example.openai.math.entity

data class ImageAnalysisVO(
    val imageUrl: String,
    val analysisText: String,
    val youtubeUrls: List<String>,
)
