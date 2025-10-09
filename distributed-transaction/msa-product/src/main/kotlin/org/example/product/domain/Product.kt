package org.example.product.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Version

@Entity
@Table(name = "products")
class Product(
    val price: Long,
    var quantity: Long,
    var reservedQuantity: Long,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Version
    private var version: Long = 0


    fun calculatePrice(quantity: Long) = price * quantity

    fun buy(quantity: Long) {
        if (this.quantity < quantity) {
            throw RuntimeException("재고가 부족합니다.")
        }

        this.quantity -= quantity
    }

    fun reserve(requestedQuantity: Long): Long {
        val reservableQuantity = this.quantity - this.reservedQuantity

        if (reservableQuantity < requestedQuantity) {
            throw RuntimeException("예약 할 수 있는 수량이 부족합니다.")
        }

        this.reservedQuantity += requestedQuantity

        return price * requestedQuantity
    }

    fun confirm(requestedQuantity: Long) {
        if (this.quantity < requestedQuantity) {
            throw RuntimeException("재고가 부족합니다.")
        }

        if (this.reservedQuantity < requestedQuantity) {
            throw RuntimeException("예약된 수량이 부족합니다.")
        }

        this.quantity -= requestedQuantity
        this.reservedQuantity -= requestedQuantity
    }

    fun cancel(requestedQuantity: Long) {
        if (this.reservedQuantity < requestedQuantity) {
            throw RuntimeException("예약된 수량이 부족합니다.")
        }

        this.reservedQuantity -= requestedQuantity
    }
}