package org.example.order.infrastructure.product

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException.BadRequest
import org.springframework.web.client.HttpClientErrorException.NotFound
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

    @Retryable(
        retryFor = [Exception::class],
        noRetryFor = [BadRequest::class, NotFound::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 500)
    )
    fun buy(request: ProductBuyApiRequest): ProductBuyApiResponse {
        return restClient
            .post()
            .uri("/products/buy")
            .body(request)
            .retrieve()
            .body<ProductBuyApiResponse>()!!
    }

    @Retryable(
        retryFor = [Exception::class],
        noRetryFor = [BadRequest::class, NotFound::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 500)
    )
    fun cancel(request: ProductBuyCancelApiRequest): ProductBuyCancelApiResponse {
        return restClient
            .post()
            .uri("/products/buy/cancel")
            .body(request)
            .retrieve()
            .body<ProductBuyCancelApiResponse>()!!
    }
}