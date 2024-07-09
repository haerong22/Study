package org.example.ledgerservice.ledger.application.port.`in`

import org.example.ledgerservice.ledger.domain.LedgerEventMessage
import org.example.ledgerservice.ledger.domain.PaymentEventMessage

interface DoubleLedgerEntryRecordUseCase {

    fun recordDoubleLedgerEntry(message: PaymentEventMessage): LedgerEventMessage
}