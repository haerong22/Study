package org.example.order.application.dto

data class CreateOrderCommand(
    val orderItems: List<OrderItem>,
) {

    data class OrderItem(
        val productId: Long,
        val quantity: Long,
    )
}