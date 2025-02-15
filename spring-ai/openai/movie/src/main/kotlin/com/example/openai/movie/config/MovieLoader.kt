package com.example.openai.movie.config

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
import java.util.stream.Collectors

@Configuration
class MovieLoader(
    private val vectorStore: VectorStore,
    private val jdbcClient: JdbcClient,
    @Value("classpath:movie_plots_korean.txt")
    private val resource: Resource,
) {

    @PostConstruct
    fun init() {
        val count = jdbcClient.sql("select count(*) from movie_vector")
            .query(Int::class.java)
            .single()
        println("No of Records in the PG Vector Store=$count")
        if (count == 0) {
            val documents = Files.lines(resource.file.toPath())
                .map { Document(it) }
                .collect(Collectors.toList())
            val textSplitter: TextSplitter = TokenTextSplitter()
            for (document in documents) {
                val splitteddocs: List<Document> = textSplitter.split(document)
                println("before adding document: " + document.content)
                vectorStore.add(splitteddocs)
                println("Added document: " + document.content)
            }
            println("Application is ready to Serve the Requests")
        }
    }
}