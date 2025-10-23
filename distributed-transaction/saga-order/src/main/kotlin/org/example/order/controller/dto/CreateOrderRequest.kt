package org.example.order.controller.dto

import org.example.order.application.dto.CreateOrderCommand

data class CreateOrderRequest(
    val items: List<OrderItem>,
) {

    fun toCommand() = CreateOrderCommand(
        items.map { CreateOrderCommand.OrderItem(it.productid, it.quantity) }
    )


    data class OrderItem(
        val productid: Long,
        val quantity: Long,
    )
}
