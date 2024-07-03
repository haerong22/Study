package org.example.walletservice.wallet.adapter.out.persistence.repository

import org.example.walletservice.wallet.domain.PaymentEventMessage
import org.example.walletservice.wallet.domain.WalletTransaction

interface WalletTransactionRepository {

    fun isExist(paymentEventMessage: PaymentEventMessage): Boolean

    fun save(walletTransactions: List<WalletTransaction>)
}