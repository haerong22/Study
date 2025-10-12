package org.example.point.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Version

@Entity
@Table(name = "points")
class Point(
    val userId: Long,
    var amount: Long,
    var reservedAmount: Long,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Version
    private var version: Long = 0

    fun use(amount: Long) {
        if (this.amount < amount) {
            throw RuntimeException("잔액이 부족합니다.")
        }

        this.amount -= amount
    }

    fun reserve(reserveAmount: Long) {
        val reservableAmount = this.amount - reserveAmount

        if (reservableAmount < reserveAmount) {
            throw RuntimeException("금액이 부족합니다.")
        }

        this.reservedAmount += reserveAmount
    }

    fun confirm(reserveAmount: Long) {
        if (this.amount < reserveAmount) {
            throw RuntimeException("포인트가 부족합니다.")
        }

        if (this.reservedAmount < reserveAmount) {
            throw RuntimeException("예약된 금액이 부족합니다.")
        }

        this.amount -= reserveAmount
        this.reservedAmount -= reserveAmount
    }

    fun cancel(reserveAmount: Long) {
        if (this.reservedAmount < reserveAmount) {
            throw RuntimeException("예약된 금액이 부족합니다.")
        }

        this.reservedAmount -= reserveAmount
    }
}