package org.example.order.controller.dto

import org.example.order.application.dto.PlaceOrderCommand

data class PlaceOrderRequest(
    val orderId: Long
) {
    fun toCommand() = PlaceOrderCommand(orderId)
}
