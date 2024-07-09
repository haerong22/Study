package org.example.ledgerservice.ledger.application.port.out

import org.example.ledgerservice.ledger.domain.DoubleLedgerEntry

interface SaveDoubleLedgerEntryPort {

  fun save(doubleLedgerEntries: List<DoubleLedgerEntry>)
}