package org.example.point.domain

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "point_transaction_histories")
class PointTransactionHistory(
    val requestId: String,
    val pointId: Long,
    val amount: Long,
    @Enumerated(EnumType.STRING)
    val transactionType: TransactionType,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}

enum class TransactionType {
    USE, CANCEL
}