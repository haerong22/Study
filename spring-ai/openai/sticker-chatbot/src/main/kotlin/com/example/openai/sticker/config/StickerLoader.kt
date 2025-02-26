package com.example.openai.sticker.config

import jakarta.annotation.PostConstruct
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.document.Document
import org.springframework.ai.model.Media
import org.springframework.ai.transformer.splitter.TextSplitter
import org.springframework.ai.transformer.splitter.TokenTextSplitter
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.util.MimeTypeUtils


@Configuration
class StickerLoader(
    private val vectorStore: VectorStore,
    private val jdbcClient: JdbcClient,
    private val chatModel: ChatModel,
) {

    @PostConstruct
    fun init() {
        val count = jdbcClient.sql("select count(*) from sticker_vector")
            .query(Int::class.java)
            .single()

        if (count == 0) {
            val documents = mutableListOf<Document>()
            for (i in 1..5) {
                val path = "sticker/$i.png"
                val desc = stickerDesc(path)
                val metadata = mapOf("path" to path)
                println(desc)
                documents.add(Document(desc, metadata))
            }
            save(documents)
        }
    }

    private fun save(documents: List<Document>) {
        val textSplitter: TextSplitter = TokenTextSplitter()
        for (document in documents) {
            val splitteddocs: List<Document> = textSplitter.split(document)
            println("before adding document: " + document.content)
            vectorStore.add(splitteddocs) //임베딩
            println("Added document: " + document.content)
        }
        println("Application is ready to Serve the Requests")
    }

    private fun stickerDesc(path: String): String {
        val imageResource = ClassPathResource(path)

        val userMessage = UserMessage(
            "이 스티커를 분석해줘",
            Media(MimeTypeUtils.IMAGE_PNG, imageResource)
        )

        val response: ChatResponse = chatModel.call(Prompt(userMessage))

        return response.result.output.content
    }

}