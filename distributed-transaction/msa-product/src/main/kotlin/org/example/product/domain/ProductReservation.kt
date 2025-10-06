package org.example.product.domain

import jakarta.persistence.Entity
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
    val status: ProductReservationStatus = ProductReservationStatus.RESERVED,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null


}

enum class ProductReservationStatus {
    RESERVED, CONFIRMED, CANCELLED
}