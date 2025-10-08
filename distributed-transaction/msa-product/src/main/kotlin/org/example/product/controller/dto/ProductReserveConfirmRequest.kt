package org.example.product.controller.dto

import org.example.product.application.dto.ProductReserveConfirmCommand

data class ProductReserveConfirmRequest(
    val requestId: String,
) {

    fun toCommand() = ProductReserveConfirmCommand(requestId)
}