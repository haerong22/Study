package org.example.point.application.dto

data class PointReserveCommand(
    val requestId: String,
    val userId: Long,
    val reserveAmount: Long,
)
