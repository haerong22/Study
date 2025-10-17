package org.example.order.infrastructure.product

import org.example.order.infrastructure.product.dto.ProductReserveApiRequest
import org.example.order.infrastructure.product.dto.ProductReserveApiResponse
import org.example.order.infrastructure.product.dto.ProductReserveCancelApiRequest
import org.example.order.infrastructure.product.dto.ProductReserveConfirmApiRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import java.time.Duration

@Component
class ProductApiClient(
    @param:Value("\${api-client.product}") private val productUrl: String,
) {
    private val restClient = RestClient.builder()
        .requestFactory(
            HttpComponentsClientHttpRequestFactory().apply {
                setReadTimeout(Duration.ofSeconds(2))
            }
        )
        .baseUrl(productUrl)
        .build()

    fun reserveProduct(request: ProductReserveApiRequest): ProductReserveApiResponse {
        return restClient.post()
            .uri("/products/reserve")
            .body(request)
            .retrieve()
            .body<ProductReserveApiResponse>()!!
    }

    fun confirmProduct(request: ProductReserveConfirmApiRequest) {
        restClient.post()
            .uri("/products/confirm")
            .body(request)
            .retrieve()
            .toBodilessEntity()
    }

    fun cancelProduct(request: ProductReserveCancelApiRequest) {
        restClient.post()
            .uri("/products/confirm")
            .body(request)
            .retrieve()
            .toBodilessEntity()
    }

}