package org.example.order.infrastructure.product

import org.example.order.infrastructure.product.dto.ProductReserveApiRequest
import org.example.order.infrastructure.product.dto.ProductReserveApiResponse
import org.example.order.infrastructure.product.dto.ProductReserveCancelApiRequest
import org.example.order.infrastructure.product.dto.ProductReserveConfirmApiRequest
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

class ProductApiClient(
    private val restClient: RestClient,
) {

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