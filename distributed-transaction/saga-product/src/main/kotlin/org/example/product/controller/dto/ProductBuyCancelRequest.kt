package org.example.product.controller.dto

import org.example.product.application.dto.ProductBuyCancelCommand

data class ProductBuyCancelRequest(
    val requestId: String,
) {
    fun toCommand() = ProductBuyCancelCommand(requestId)
}
