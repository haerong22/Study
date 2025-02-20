package com.example.openai.hotel.service

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux


@Service
class HotelService(
    private val vectorStore: VectorStore,
    chatClientBuilder: ChatClient.Builder,
) {
    var chatClient: ChatClient = chatClientBuilder.build()

    fun chat(question: String): Flux<String> {
        println(question)

        val results: List<Document> = vectorStore.similaritySearch(
            SearchRequest
                .query(question)
                .withSimilarityThreshold(0.5)
                .withTopK(1)
        )

        val template = """
                당신은 어느 호텔 직원입니다. 문맥에 따라 고객의 질문에 정중하게 답변해 주십시오. 
                컨텍스트가 질문에 대답할 수 없는 경우 '모르겠습니다'라고 대답하세요.
                               
                컨텍스트:
                 {context}
                 질문: 
                 {question}
                 
                 답변:
                
                """.trimIndent()

        return chatClient.prompt()
            .user { promptUserSpec ->
                promptUserSpec.text(template)
                    .param("context", results)
                    .param("question", question)
            }
            .stream()
            .content()
    }
}