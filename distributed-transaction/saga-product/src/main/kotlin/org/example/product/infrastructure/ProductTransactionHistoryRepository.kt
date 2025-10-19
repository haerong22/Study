package org.example.product.infrastructure

import org.example.product.domain.ProductTransactionHistory
import org.example.product.domain.TransactionType
import org.springframework.data.jpa.repository.JpaRepository

interface ProductTransactionHistoryRepository: JpaRepository<ProductTransactionHistory, Long> {

    fun findAllByRequestIdAndTransactionType(requestId: String, transactionType: TransactionType): List<ProductTransactionHistory>
}