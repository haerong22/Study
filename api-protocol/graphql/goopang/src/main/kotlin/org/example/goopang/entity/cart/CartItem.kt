package org.example.goopang.entity.cart

import org.example.goopang.entity.product.Product

data class CartItem(
    val id: String,
    val quantity: Int,
    val product: Product,
    val cart: Cart,
) {
}