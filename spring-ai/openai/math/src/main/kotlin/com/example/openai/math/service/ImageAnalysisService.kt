package com.example.openai.math.service

import org.springframework.ai.chat.messages.SystemMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.model.Media
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.util.MimeType
import org.springframework.util.MimeTypeUtils
import org.springframework.web.multipart.MultipartFile

@Service
class ImageAnalysisService(
    private val chatModel: ChatModel,
    @Value("classpath:/system.message")
    private val defaultSystemMessage: Resource
) {

    fun analyzeImage(imageFile: MultipartFile, message: String): String {
        // MIME 타입 결정
        val contentType = imageFile.contentType

        require(!(MimeTypeUtils.IMAGE_PNG_VALUE != contentType && MimeTypeUtils.IMAGE_JPEG_VALUE != contentType)) { "지원되지 않는 이미지 형식입니다." }

        try {
            // Media 객체 생성
            val media = Media(MimeType.valueOf(contentType!!), imageFile.resource)
            // 사용자 메시지 생성
            val userMessage = UserMessage(message, media)
            // 시스템 메시지 생성
            val systemMessage = SystemMessage(defaultSystemMessage)
            // AI 모델 호출
            return chatModel.call(userMessage, systemMessage)
        } catch (e: Exception) {
            throw RuntimeException("이미지 처리 중 오류가 발생했습니다.", e)
        }
    }
}