package org.example.product.consumer.dto

data class OrderPlacedEvent(
    val orderId: Long,
    val productInfos: List<ProductInfo>,
) {

    data class ProductInfo(
        val productId: Long,
        val quantity: Long,
    )
}
