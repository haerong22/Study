package com.example.openai.recipe.service

import com.example.openai.recipe.entity.Recipe
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI


@Service
class RecipeService(
    private val chatModel: ChatModel,
    @Value("\${google.api-key}")
    private val googleApiKey: String,
    @Value("\${google.search-engine-id}")
    private val googleCx: String,
    @Value("\${google.api-url}")
    private val apiUrl: String,
) {
    private val restTemplate: RestTemplate = RestTemplate()

    fun createRecipe(recipe: Recipe): String {
        val template = """
            제목: 요리 제목을 제공해 주세요.
            다음 재료를 사용하여 요리법을 만들고 싶습니다: {ingredients}.
            선호하는 요리 유형은 {cuisine}입니다.
            다음 식이 제한을 고려해 주세요: {dietaryRestrictions}.
            재료 목록과 조리법을 포함한 상세한 요리법을 제공해 주세요.
        """.trimIndent()

        val promptTemplate = PromptTemplate(template)
        val params = mapOf(
            "ingredients" to recipe.ingredients,  // 재료
            "cuisine" to recipe.cuisine,  // 요리
            "dietaryRestrictions" to recipe.dietaryRestrictions // 식이제한
        )

        val prompt = promptTemplate.create(params)
        return chatModel.call(prompt).result.output.content
    }

    fun searchRecipeUrls(query: String): List<String> {
        val apiUrl: URI = UriComponentsBuilder.fromUriString(apiUrl)
            .queryParam("key", googleApiKey)
            .queryParam("cx", googleCx)
            .queryParam("q", query)
            .build()
            .toUri()
        println(apiUrl.toString())

        val response = restTemplate.getForObject(apiUrl, String::class.java)

        val jsonResponse: JsonObject = JsonParser.parseString(response).asJsonObject
        println(jsonResponse.toString())

        val itemsArray = jsonResponse.getAsJsonArray("items")

        val urls: MutableList<String> = ArrayList()
        if (itemsArray != null) {
            for (item in itemsArray) {
                urls.add(item.asJsonObject["link"].asString)
            }
        } else {
            println("No search results found for the query: $query")
        }
        return urls
    }

    fun createRecipeWithUrls(recipe: Recipe): Map<String, Any> {
        val recipeContent = createRecipe(recipe) // 레시피 설명 생성
        val urls = searchRecipeUrls(recipe.ingredients) // Google Custom Search API로 URL 검색
        return mapOf("recipe" to recipeContent, "urls" to urls)
    }
}