package org.example.order.infrastructure.point

data class PointUseApiRequest(
    val requestId: String,
    val userId: Long,
    val amount: Long,
)
