package com.example.openai.sticker.service

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux


@Service
class StickerService(
    private val vectorStore: VectorStore,
    chatClientBuilder: ChatClient.Builder,
) {
    var chatClient: ChatClient = chatClientBuilder.build()

    fun chat(question: String): ChatResponse {
        println(question)

        val template = """
                당신은 친구와 자연스럽게 일상 대화를 하는 상황입니다. 질문에 따라 대화를 이어 나가면 됩니다.
                
                질문: 
                {question}
                 
                답변:
                
                """.trimIndent()

        val content = chatClient.prompt()
            .user { promptUserSpec ->
                promptUserSpec.text(template)
                    .param("question", question)
            }
            .call()
            .content()

        val results: List<Document> = vectorStore.similaritySearch(
            SearchRequest
                .query(question)
                .withSimilarityThreshold(0.7)
                .withTopK(1)
        )

        return ChatResponse(
            sticker = results[0].content,
            text = content!!
        )
    }
}