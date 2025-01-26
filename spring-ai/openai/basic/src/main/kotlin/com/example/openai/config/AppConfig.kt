package com.example.openai.config

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor
import org.springframework.ai.chat.memory.InMemoryChatMemory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource

@Configuration
class AppConfig(
    @Value("classpath:/prompt.txt")
    private val resource: Resource
) {

    @Bean
    fun chatClient(chatClientBuilder: ChatClient.Builder): ChatClient {
        return chatClientBuilder
//            .defaultSystem("당신은 교육 튜터입니다. 개념을 명확하고 간단하게 설명하세요")
//            .defaultSystem(resource)
//            .defaultAdvisors(MessageChatMemoryAdvisor(InMemoryChatMemory()))
            .build()
    }
}