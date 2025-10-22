package org.example.point.controller.dto

import org.example.point.application.dto.PointUseCancelCommand

data class PointUseCancelRequest(
    val requestId: String,
) {

    fun toCommand() = PointUseCancelCommand(requestId)
}
