package org.example.point.controller.dto

import org.example.point.application.dto.PointReserveCancelCommand

data class PointReserveCancelRequest(
    val requestId: String,
) {

    fun toCommand() = PointReserveCancelCommand(requestId)
}