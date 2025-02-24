package com.example.openai.image.service

import com.example.openai.image.entity.ImageRequestDTO
import org.springframework.ai.image.ImagePrompt
import org.springframework.ai.openai.OpenAiImageModel
import org.springframework.ai.openai.OpenAiImageOptions
import org.springframework.stereotype.Service

@Service
class ImageService(
    private val openAiImageModel: OpenAiImageModel,
) {

    fun getImageGen(request: ImageRequestDTO): List<String> {
        return openAiImageModel
            .call(
                ImagePrompt(
                    request.message,
                    OpenAiImageOptions.builder()
                        .withModel(request.model) // 모델설정
                        .withQuality("hd") // 이미지 품질
                        .withN(request.n) // 생성할 이미지 개수
                        .withHeight(1024)
                        .withWidth(1024)
                        .build()
                )
            )
            .results
            .map { it.output.url }
            .toList()
    }
}