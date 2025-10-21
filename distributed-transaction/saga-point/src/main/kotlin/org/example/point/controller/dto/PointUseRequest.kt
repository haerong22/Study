package org.example.point.controller.dto

import org.example.point.application.dto.PointUseCommand

data class PointUseRequest(
    val requestId: String,
    val userId: Long,
    val amount: Long,
) {

    fun toCommand() = PointUseCommand(requestId, userId, amount)
}
