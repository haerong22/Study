package org.example.ledgerservice.ledger.adapter.out.persistence.repository

import org.example.ledgerservice.ledger.domain.DoubleAccountsForLedger
import org.example.ledgerservice.ledger.domain.FinanceType

interface AccountRepository {

  fun getDoubleAccountsForLedger(financeType: FinanceType): DoubleAccountsForLedger
}