package org.example.order.infrastructure.point

import org.example.order.infrastructure.point.dto.PointReserveApiRequest
import org.example.order.infrastructure.point.dto.PointReserveCancelApiRequest
import org.example.order.infrastructure.point.dto.PointReserveConfirmApiRequest
import org.springframework.web.client.RestClient

class PointApiClient(
    private val restClient: RestClient,
) {

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