package org.example.point.controller.dto

import org.example.point.application.dto.PointReserveConfirmCommand

data class PointReserveConfirmRequest(
    val requestId: String,
) {

    fun toCommand() = PointReserveConfirmCommand(requestId)
}