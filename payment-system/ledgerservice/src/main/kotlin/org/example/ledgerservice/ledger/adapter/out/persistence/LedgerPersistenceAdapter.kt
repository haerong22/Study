package org.example.ledgerservice.ledger.adapter.out.persistence

import org.example.ledgerservice.common.PersistenceAdapter
import org.example.ledgerservice.ledger.adapter.out.persistence.repository.LedgerEntryRepository
import org.example.ledgerservice.ledger.adapter.out.persistence.repository.LedgerTransactionRepository
import org.example.ledgerservice.ledger.application.port.out.DuplicateMessageFilterPort
import org.example.ledgerservice.ledger.application.port.out.SaveDoubleLedgerEntryPort
import org.example.ledgerservice.ledger.domain.DoubleLedgerEntry
import org.example.ledgerservice.ledger.domain.PaymentEventMessage

@PersistenceAdapter
class LedgerPersistenceAdapter(
    private val ledgerTransactionRepository: LedgerTransactionRepository,
    private val ledgerEntryRepository: LedgerEntryRepository,
) : DuplicateMessageFilterPort, SaveDoubleLedgerEntryPort {

    override fun isAlreadyProcess(message: PaymentEventMessage): Boolean {
        return ledgerTransactionRepository.isExist(message)
    }

    override fun save(doubleLedgerEntries: List<DoubleLedgerEntry>) {
        return ledgerEntryRepository.save(doubleLedgerEntries)
    }
}