package org.example.order.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "orders")
class Order(
    var status: OrderStatus = OrderStatus.CREATED
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun isCreated() = status == OrderStatus.CREATED
    fun isRequested() = status == OrderStatus.REQUESTED
    fun isCompleted() = this.status == OrderStatus.COMPLETED

    fun request() {
        if (!isCreated()) {
            throw RuntimeException("잘못된 요청입니다.")
        }

        this.status = OrderStatus.REQUESTED
    }

    fun complete() {
        this.status = OrderStatus.COMPLETED
    }

    fun fail() {
        if (!isRequested()) {
            throw RuntimeException("잘못된 요청입니다.")
        }

        this.status = OrderStatus.FAILED
    }
}

enum class OrderStatus {
    CREATED, REQUESTED, COMPLETED, FAILED
}