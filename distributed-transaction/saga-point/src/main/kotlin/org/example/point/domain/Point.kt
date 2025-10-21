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
) {

    @Version
    private var version: Long = 0

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun use(amount: Long) {
        if (this.amount < amount) {
            throw RuntimeException("잔액이 부족합니다.")
        }

        this.amount -= amount
    }
}