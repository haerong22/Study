package org.example.point.infrastructure

import org.example.point.domain.PointReservation
import org.springframework.data.jpa.repository.JpaRepository

interface PointReservationRepository: JpaRepository<PointReservation, Long> {

    fun findByRequestId(requestId: String): PointReservation?
}