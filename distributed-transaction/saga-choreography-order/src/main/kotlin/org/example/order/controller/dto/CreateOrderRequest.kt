package org.example.order.controller.dto

import org.example.order.application.dto.CreateOrderCommand

data class CreateOrderRequest(
    val items: List<OrderItem>,
) {

    fun toCommand() = CreateOrderCommand(
        items.map { CreateOrderCommand.OrderItem(it.productId, it.quantity) }
    )


    data class OrderItem(
        val productId: Long,
        val quantity: Long,
    )
}
