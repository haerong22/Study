package org.example.point.controller.dto

import org.example.point.application.dto.PointReserveCommand

data class PointReserveRequest(
    val requestId: String,
    val userId: Long,
    val reserveAmount: Long,
) {

    fun toCommand() = PointReserveCommand(requestId, userId, reserveAmount)
}