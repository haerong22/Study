package org.example.product.controller.dto

import org.example.product.application.dto.ProductReserveCancelCommand

data class ProductReserveCancelRequest(
    val requestId: String,
) {

    fun toCommand() = ProductReserveCancelCommand(requestId)
}