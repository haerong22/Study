package org.example.order.infrastructure.point.dto

data class PointReserveApiRequest(
    val requestId: String,
    val userId: Long,
    val reserveAmount: Long,
)