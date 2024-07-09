package org.example.ledgerservice.ledger.application.port.out

import org.example.ledgerservice.ledger.domain.DoubleAccountsForLedger
import org.example.ledgerservice.ledger.domain.FinanceType

interface LoadAccountPort {

  fun getDoubleAccountsForLedger(financeType: FinanceType): DoubleAccountsForLedger
}