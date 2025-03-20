package org.example.goopang.entity.cart

import org.example.goopang.entity.user.User

data class Cart(
    val id: String,
    val user: User,
) {
    var totalAmount: Double = 0.0
    var items: List<CartItem> = listOf()
}