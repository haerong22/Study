package org.example.product.infrastructure

import org.example.product.domain.ProductReservation
import org.springframework.data.jpa.repository.JpaRepository

interface ProductReservationRepository : JpaRepository<ProductReservation, Long> {

    fun findAllByRequestId(requestId: String): List<ProductReservation>
}