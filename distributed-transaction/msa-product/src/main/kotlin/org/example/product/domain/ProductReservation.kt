package org.example.product.domain

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "product_reservations")
class ProductReservation(
    val requestId: String,
    val productId: Long,
    val reservedQuantity: Long,
    val reservationPrice: Long,
    @Enumerated(EnumType.STRING)
    var status: ProductReservationStatus = ProductReservationStatus.RESERVED,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun isConfirmed() = status == ProductReservationStatus.CONFIRMED
    fun isCancelled() = status == ProductReservationStatus.CANCELLED

    fun confirm() {
        if (isCancelled()) {
            throw RuntimeException("이미 취소된 예약입니다.")
        }

        this.status = ProductReservationStatus.CONFIRMED
    }
}

enum class ProductReservationStatus {
    RESERVED, CONFIRMED, CANCELLED
}