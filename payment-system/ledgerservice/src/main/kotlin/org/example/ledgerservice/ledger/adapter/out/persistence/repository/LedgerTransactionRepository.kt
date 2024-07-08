package org.example.ledgerservice.ledger.adapter.out.persistence.repository

import org.example.ledgerservice.ledger.domain.PaymentEventMessage


interface LedgerTransactionRepository {

  fun isExist(message: PaymentEventMessage): Boolean
}