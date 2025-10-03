package org.example.monolithic.order.controller.dto

import org.example.monolithic.order.application.dto.PlaceOrderCommand

data class PlaceOrderRequest(
    val orderId: Long,
) {

    fun toCommand(): PlaceOrderCommand {
        return PlaceOrderCommand(orderId)
    }
}
