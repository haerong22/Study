package org.example.point.domain

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "point_reservations")
class PointReservation(
    val requestId: String,
    val pointId: Long,
    val reservationAmount: Long,
    @Enumerated(EnumType.STRING)
    var status: PointReservationStatus = PointReservationStatus.RESERVED,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun isConfirmed() = status == PointReservationStatus.CONFIRMED
    fun isCancelled() = status == PointReservationStatus.CANCELLED

    fun confirm() {
        if (isCancelled()) {
            throw RuntimeException("이미 취소된 예약입니다.")
        }

        this.status = PointReservationStatus.CONFIRMED
    }

    fun cancel() {
        if (isConfirmed()) {
            throw RuntimeException("이미 확정된 예약입니다.")
        }

        this.status = PointReservationStatus.CANCELLED
    }
}

enum class PointReservationStatus {
    RESERVED, CONFIRMED, CANCELLED
}