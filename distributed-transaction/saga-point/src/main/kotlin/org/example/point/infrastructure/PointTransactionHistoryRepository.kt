package org.example.point.infrastructure

import org.example.point.domain.PointTransactionHistory
import org.example.point.domain.TransactionType
import org.springframework.data.jpa.repository.JpaRepository

interface PointTransactionHistoryRepository: JpaRepository<PointTransactionHistory, Long> {

    fun findByRequestIdAndTransactionType(requestId: String, transactionType: TransactionType): PointTransactionHistory?
}