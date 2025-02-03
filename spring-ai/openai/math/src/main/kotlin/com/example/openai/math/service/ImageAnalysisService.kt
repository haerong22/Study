package com.example.openai.math.service

import org.json.JSONArray
import org.json.JSONObject
import org.springframework.ai.chat.messages.SystemMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.model.Media
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.util.MimeType
import org.springframework.util.MimeTypeUtils
import org.springframework.web.client.RestTemplate
import org.springframework.web.multipart.MultipartFile


@Service
class ImageAnalysisService(
    private val chatModel: ChatModel,
    @Value("classpath:/system.message")
    private val defaultSystemMessage: Resource,
    @Value("\${youtube.api-key}")
    private val youtubeApiKey: String,
    @Value("\${youtube.api-url}")
    private val youtubeApiUrl: String,
) {

    fun analyzeImage(imageFile: MultipartFile, message: String): String {
        // MIME 타입 결정
        val contentType = imageFile.contentType

        require(!(MimeTypeUtils.IMAGE_PNG_VALUE != contentType && MimeTypeUtils.IMAGE_JPEG_VALUE != contentType)) { "지원되지 않는 이미지 형식입니다." }

        // Media 객체 생성
        val media = Media(MimeType.valueOf(contentType!!), imageFile.resource)
        // 사용자 메시지 생성
        val userMessage = UserMessage(message, media)
        // 시스템 메시지 생성
        val systemMessage = SystemMessage(defaultSystemMessage)
        // AI 모델 호출
        return chatModel.call(userMessage, systemMessage)
    }

    fun searchYouTubeVideos(query: String?): List<String> {
        if (query == null) return emptyList()

        val url = "${youtubeApiUrl}?part=snippet&type=video&q=EBS ${query}&order=relevance&key=${youtubeApiKey}"

        val restTemplate = RestTemplate()
        val response = restTemplate.getForEntity(url, String::class.java)
        println(response.body)

        val videoUrls: MutableList<String> = ArrayList()
        val jsonResponse = JSONObject(response.body)
        val items: JSONArray = jsonResponse.getJSONArray("items")

        for (i in 0 until items.length()) {
            val item: JSONObject = items.getJSONObject(i)
            val videoId: String = item.getJSONObject("id").getString("videoId")
            videoUrls.add(videoId)
        }
        return videoUrls
    }

    fun extractKeyYouTubeSearch(analysisText: String): String? {
        var keyword: String? = null
        if (analysisText.indexOf("핵심 키워드:") != -1) {
            keyword = analysisText.substring(analysisText.indexOf("핵심 키워드:")).split(":")[1].trim()
        }
        return keyword
    }
}