package org.example.product.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "products")
class Product(
    var quantity: Long,
    val price: Long,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null


    fun calculatePrice(quantity: Long) = price * quantity

    fun buy(quantity: Long) {
        if (this.quantity < quantity) {
            throw RuntimeException("재고가 부족합니다.")
        }

        this.quantity -= quantity
    }

    fun cancel(quantity: Long) {
        this.quantity += quantity
    }
}