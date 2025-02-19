package com.example.openai.hotel.config

import jakarta.annotation.PostConstruct
import org.springframework.ai.document.Document
import org.springframework.ai.transformer.splitter.TextSplitter
import org.springframework.ai.transformer.splitter.TokenTextSplitter
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.jdbc.core.simple.JdbcClient
import java.nio.file.Files

@Configuration
class HotelLoader(
    private val vectorStore: VectorStore,
    private val jdbcClient: JdbcClient,
    @Value("classpath:data.txt")
    private val resource: Resource,
) {

    @PostConstruct
    fun init() {
        val count = jdbcClient.sql("select count(*) from hotel_vector")
            .query(Int::class.java)
            .single()
        println("No of Records in the PG Vector Store=$count")
        if (count == 0) {
            val documents: List<Document> = Files.lines(resource.file.toPath())
                .map { Document(it) }
                .toList()
            val textSplitter: TextSplitter = TokenTextSplitter()
            for (document in documents) {
                val splitteddocs: List<Document> = textSplitter.split(document)
                println("before adding document: " + document.content)
                vectorStore.add(splitteddocs) //임베딩
                println("Added document: " + document.content)
                Thread.sleep(1000) // 1초
            }
            println("Application is ready to Serve the Requests")
        }
    }
}