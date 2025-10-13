package org.example.order.domain

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "orders")
class Order(
    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.CREATED
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun isCompleted() = this.status == OrderStatus.COMPLETED
    fun isCreated() = this.status == OrderStatus.CREATED
    fun isReserved() = this.status == OrderStatus.RESERVED
    fun isPending() = this.status == OrderStatus.PENDING

    fun complete() {
        this.status = OrderStatus.COMPLETED
    }

    fun reserve() {
        if (!isCreated()) {
            throw RuntimeException("생성된 단계에서만 예약할 수 있습니다.")
        }

        this.status = OrderStatus.RESERVED
    }

    fun cancel() {
        if (!isReserved()) {
            throw RuntimeException("예약 단계에서만 취소 할 수 있습니다.")
        }

        this.status = OrderStatus.CANCELLED
    }

    fun confirm() {
        if (!isReserved() && !isPending()) {
            throw RuntimeException("예약/대기 단계에서만 확정 할 수 있습니다.")
        }

        this.status = OrderStatus.CONFIRMED
    }

    fun pending() {
        if (!isReserved()) {
            throw RuntimeException("예약 단계에서만 확정 할 수 있습니다.")
        }

        this.status = OrderStatus.PENDING
    }
}

enum class OrderStatus {
    CREATED, COMPLETED, RESERVED, CANCELLED, CONFIRMED, PENDING
}