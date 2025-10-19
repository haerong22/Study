package org.example.product.domain

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "product_transaction_histories")
class ProductTransactionHistory(
    val requestId: String,
    val productId: Long,
    val quantity: Long,
    val price: Long,
    @Enumerated(EnumType.STRING)
    val transactionType: TransactionType,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}

enum class TransactionType {
    PURCHASE, CANCEL
}