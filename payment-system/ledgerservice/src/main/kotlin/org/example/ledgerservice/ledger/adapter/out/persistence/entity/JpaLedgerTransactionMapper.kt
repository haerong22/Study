package org.example.ledgerservice.ledger.adapter.out.persistence.entity

import org.example.ledgerservice.common.IdempotencyCreator
import org.example.ledgerservice.ledger.domain.LedgerTransaction
import org.springframework.stereotype.Component

@Component
class JpaLedgerTransactionMapper {

  fun mapToJpaEntity(ledgerTransaction: LedgerTransaction): JpaLedgerTransactionEntity {
    return JpaLedgerTransactionEntity(
      description = "LedgerService record transaction",
      referenceType = ledgerTransaction.referenceType.name,
      referenceId = ledgerTransaction.referenceId,
      orderId = ledgerTransaction.orderId,
      idempotencyKey = IdempotencyCreator.create(ledgerTransaction)
    )
  }
}