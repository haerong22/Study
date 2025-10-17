package org.example.order.infrastructure.point

import org.example.order.infrastructure.point.dto.PointReserveApiRequest
import org.example.order.infrastructure.point.dto.PointReserveCancelApiRequest
import org.example.order.infrastructure.point.dto.PointReserveConfirmApiRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException.BadRequest
import org.springframework.web.client.HttpClientErrorException.NotFound
import org.springframework.web.client.RestClient
import java.time.Duration

@Component
class PointApiClient(
    @param:Value("\${api-client.point}") private val pointUrl: String,
) {
    private val restClient = RestClient.builder()
        .requestFactory(
            HttpComponentsClientHttpRequestFactory().apply {
                setReadTimeout(Duration.ofSeconds(2))
            }
        )
        .baseUrl(pointUrl)
        .build()

    @Retryable(
        retryFor = [Exception::class],
        noRetryFor = [BadRequest::class, NotFound::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 500)
    )
    fun reservePoint(request: PointReserveApiRequest) {
        restClient.post()
            .uri("/points/reserve")
            .body(request)
            .retrieve()
            .toBodilessEntity()
    }

    fun confirmPoint(request: PointReserveConfirmApiRequest) {
        restClient.post()
            .uri("/points/confirm")
            .body(request)
            .retrieve()
            .toBodilessEntity()
    }

    fun cancelPoint(request: PointReserveCancelApiRequest) {
        restClient.post()
            .uri("/points/confirm")
            .body(request)
            .retrieve()
            .toBodilessEntity()
    }
}