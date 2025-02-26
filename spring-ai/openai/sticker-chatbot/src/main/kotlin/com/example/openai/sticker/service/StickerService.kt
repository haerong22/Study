package com.example.openai.sticker.service

import org.springframework.ai.chat.client.ChatClient
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

    fun chat(question: String): Flux<String> {
        val template = """
                당신은 친구와 자연스럽게 일상 대화를 하는 상황입니다. 질문에 따라 대화를 이어 나가면 됩니다.
                
                질문: 
                {question}
                 
                답변:
                
                """.trimIndent()

        val responseCollector = ArrayList<String>()

        return chatClient.prompt()
            .user { promptUserSpec ->
                promptUserSpec.text(template)
                    .param("question", question)
            }
            .stream()
            .content()
            .doOnNext { content ->
                responseCollector.add(content)
            }
            .concatWith(Flux.defer {
                val combinedContent = responseCollector.joinToString(" ")
                val results = vectorStore.similaritySearch(
                    SearchRequest
                        .query(combinedContent)
                        .withSimilarityThreshold(0.7)
                        .withTopK(1)
                )
                Flux.just("\n", results[0].metadata["path"].toString())
            })

    }
}