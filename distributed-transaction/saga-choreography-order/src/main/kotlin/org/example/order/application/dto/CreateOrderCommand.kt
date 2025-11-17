package org.example.order.application.dto

data class CreateOrderCommand(
    val items: List<OrderItem>,
) {

    data class OrderItem(
        val productid: Long,
        val quantity: Long,
    )
}
