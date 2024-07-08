package org.example.ledgerservice.ledger.adapter.out.persistence.repository

import org.example.ledgerservice.ledger.adapter.out.persistence.entity.JpaLedgerTransactionEntity
import org.example.ledgerservice.ledger.domain.PaymentEventMessage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class JpaLedgerTransactionRepository (
  private val springDataJpaLedgerTransactionRepository: SpringDataJpaLedgerTransactionRepository
) : LedgerTransactionRepository {

  override fun isExist(message: PaymentEventMessage): Boolean {
    return springDataJpaLedgerTransactionRepository.existsByOrderId(message.orderId())
  }
}

interface SpringDataJpaLedgerTransactionRepository : JpaRepository<JpaLedgerTransactionEntity, Long> {

  fun existsByOrderId(orderId: String): Boolean
}