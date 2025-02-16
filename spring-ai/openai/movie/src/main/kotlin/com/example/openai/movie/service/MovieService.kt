package com.example.openai.movie.service

import org.json.JSONArray
import org.json.JSONObject
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.SearchRequest.query
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
class MovieService(
    private val vectorStore: VectorStore,
    @Value("\${youtube.api-url}")
    private val apiUrl: String,
    @Value("\${youtube.api-key}")
    private val apiKey: String,
    chatClientBuilder: ChatClient.Builder,
) {
    init {
        chatClientBuilder.build()
    }

    fun recommend(query: String): MovieRecommend? {
        val results = vectorStore.similaritySearch(query(query).withSimilarityThreshold(0.85).withTopK(1))
        if (results.isNotEmpty()) {
            val topResult: Document = results[0]
            val movieContent = topResult.content
            val title = movieContent.substring(movieContent.indexOf("(") + 1, movieContent.lastIndexOf(")")) //(쇼생크탈출)
            val urls = searchYoutube(title)
            return MovieRecommend(title, movieContent, urls)
        }
        return null
    }

    private fun searchYoutube(movieTitle: String): List<String> {
        val url = "$apiUrl?part=snippet&type=video&q=$movieTitle&order=relevance&key=$apiKey"

        val restTemplate = RestTemplate()
        val response: ResponseEntity<String> = restTemplate.getForEntity(url, String::class.java)

        val videoUrls: MutableList<String> = ArrayList()
        val jsonResponse = JSONObject(response.body)
        val items: JSONArray = jsonResponse.getJSONArray("items")

        for (i in 0 until items.length()) {
            val item: JSONObject = items.getJSONObject(i)
            val videoId: String = item.getJSONObject("id").getString("videoId")
            videoUrls.add(videoId)
        }

        return videoUrls
    }
}