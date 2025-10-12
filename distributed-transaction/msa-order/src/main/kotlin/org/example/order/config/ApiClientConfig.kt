package org.example.order.config

import org.example.order.infrastructure.point.PointApiClient
import org.example.order.infrastructure.product.ProductApiClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class ApiClientConfig(
    @param:Value("\${api-client.point}") private val pointUrl: String,
    @param:Value("\${api-client.product}") private val productUrl: String,
) {

    @Bean
    fun productApiClient(): ProductApiClient {
        return ProductApiClient(
            RestClient.builder()
                .baseUrl(productUrl)
                .build()
        )
    }

    @Bean
    fun pointApiClient(): PointApiClient {
        return PointApiClient(
            RestClient.builder()
                .baseUrl(pointUrl)
                .build()
        )
    }
}