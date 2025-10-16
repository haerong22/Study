package org.example.order.controller.dto

import org.example.order.application.dto.CreateOrderCommand


data class CreateOrderRequest(
    val orderItems: List<OrderItem>,
) {

    fun toCommand(): CreateOrderCommand {
        return CreateOrderCommand(
            orderItems.map { CreateOrderCommand.OrderItem(it.productId, it.quantity) },
        )
    }

    data class OrderItem(
        val productId: Long,
        val quantity: Long,
    )
}
