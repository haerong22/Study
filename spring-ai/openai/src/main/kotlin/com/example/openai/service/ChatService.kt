package com.example.openai.service

import org.springframework.ai.chat.client.ChatClient
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
}