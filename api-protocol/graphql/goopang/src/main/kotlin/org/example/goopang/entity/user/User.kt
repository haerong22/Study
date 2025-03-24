package org.example.goopang.entity.user

import org.example.goopang.entity.SearchResult
import org.example.goopang.entity.cart.Cart
import java.time.OffsetDateTime

data class User(
    val id: String,
    val name: String,
    val email: String,
    val createdAt: OffsetDateTime,
): SearchResult {
    var cart: Cart? = null
}