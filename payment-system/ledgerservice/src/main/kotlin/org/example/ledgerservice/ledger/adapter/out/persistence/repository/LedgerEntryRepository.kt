package org.example.ledgerservice.ledger.adapter.out.persistence.repository

import org.example.ledgerservice.ledger.domain.DoubleLedgerEntry

interface LedgerEntryRepository {

  fun save(doubleLedgerEntries: List<DoubleLedgerEntry>)
}