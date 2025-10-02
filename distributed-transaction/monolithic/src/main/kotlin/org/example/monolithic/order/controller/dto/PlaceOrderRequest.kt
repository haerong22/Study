package org.example.monolithic.order.controller.dto

import org.example.monolithic.order.application.dto.PlaceOrderCommand

data class PlaceOrderRequest(
    val orderItems: List<OrderItem>,
) {

    fun toCommand(): PlaceOrderCommand {
        return PlaceOrderCommand(
            orderItems.map { PlaceOrderCommand.OrderItem(it.productId, it.quantity) },
        )
    }

    data class OrderItem(
        val productId: Long,
        val quantity: Long,
    )
}
