package com.example.openai.service

import com.example.openai.entity.Answer
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.converter.ListOutputConverter
import org.springframework.core.ParameterizedTypeReference
import org.springframework.core.convert.support.DefaultConversionService
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val chatClient: ChatClient,
) {

    fun chat(message: String): String? {
        return chatClient.prompt()
            .user(message)
            .call()
            .content()
    }

    fun chatMessage(message: String): String? {
        return chatClient.prompt()
            .user(message)
            .call()
            .chatResponse()
            ?.result
            ?.output
            ?.content
    }

    fun chatPlaceholder(subject: String, tone: String, message: String): String? {
        return chatClient.prompt()
            .user(message)
            .system {
                it.param("subject", subject)
                it.param("tone", tone)
            }
            .call()
            .content()
    }

    fun chatJson(message: String): ChatResponse? {
        return chatClient.prompt()
            .user(message)
            .call()
            .chatResponse()
    }

    fun chatObject(message: String): Answer? {
        return chatClient.prompt()
            .user(message)
            .call()
            .entity(Answer::class.java)
    }

    private val recipeTemplate = "Answer for {foodName} for {question}"

    fun recipe(foodName: String, question: String): Answer? {
        return chatClient.prompt()
            .user {
                it.text(recipeTemplate)
                    .param("foodName", foodName)
                    .param("question", question)
            }
            .call()
            .entity(Answer::class.java)
    }

    fun chatList(message: String): List<String>? {
        return chatClient.prompt()
            .user(message)
            .call()
            .entity(ListOutputConverter(DefaultConversionService()))
    }

    fun chatMap(message: String): Map<String, String>? {
        return chatClient.prompt()
            .user(message)
            .call()
            .entity(object : ParameterizedTypeReference<Map<String, String>>() {})
    }
}