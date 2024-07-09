package org.example.ledgerservice.ledger.adapter.out.persistence.entity

import org.example.ledgerservice.ledger.domain.DoubleLedgerEntry
import org.springframework.stereotype.Component

@Component
class JpaLedgerEntryMapper (
  private val jpaLedgerTransactionMapper: JpaLedgerTransactionMapper,
) {

  fun mapToJpaEntity(doubleLedgerEntry: DoubleLedgerEntry): List<JpaLedgerEntryEntity> {
    val jpaLedgerTransactionEntity = jpaLedgerTransactionMapper.mapToJpaEntity(doubleLedgerEntry.transaction)
    return listOf(
      JpaLedgerEntryEntity(
        amount = doubleLedgerEntry.credit.amount.toBigDecimal(),
        accountId = doubleLedgerEntry.credit.account.id,
        type = doubleLedgerEntry.credit.type,
        transaction = jpaLedgerTransactionEntity
      ),
      JpaLedgerEntryEntity(
        amount = doubleLedgerEntry.debit.amount.toBigDecimal(),
        accountId = doubleLedgerEntry.debit.account.id,
        type = doubleLedgerEntry.debit.type,
        transaction = jpaLedgerTransactionEntity
      )
    )
  }
}