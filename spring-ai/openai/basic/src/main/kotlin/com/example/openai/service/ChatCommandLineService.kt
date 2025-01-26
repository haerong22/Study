package com.example.openai.service

import com.example.openai.entity.Answer
import com.example.openai.entity.Movie
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.converter.ListOutputConverter
import org.springframework.core.ParameterizedTypeReference
import org.springframework.core.convert.support.DefaultConversionService
import org.springframework.stereotype.Service
import java.util.Scanner

@Service
class ChatCommandLineService(
    private val chatClient: ChatClient,
) {

    fun startChat() {
        val scanner = Scanner(System.`in`)
        println("Enter your message: ")
        while (true) {
            val message = scanner.nextLine()
            if (message == "exit") {
                println("Exiting chat...")
                break
            }
            val response = getResponse(message)
            println("Bot: $response")
        }
        scanner.close()
    }

    private fun getResponse(message: String): String? {
        return chatClient.prompt()
            .user(message)
            .call()
            .content()
    }
}