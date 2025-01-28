package com.example.openai.chat.service

import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.openai.OpenAiChatOptions
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val chatModel: ChatModel,
) {

    fun getResponse(message: String): String {
        return chatModel.call(message)
    }

    fun getResponseOptions(message: String): String {
        val response = chatModel.call(
            Prompt(
                message,
                OpenAiChatOptions.builder()
                    .withModel("gpt-4o")
                    .withTemperature(0.4)
                    .build()
            )
        )
        return response.result.output.content
    }

}