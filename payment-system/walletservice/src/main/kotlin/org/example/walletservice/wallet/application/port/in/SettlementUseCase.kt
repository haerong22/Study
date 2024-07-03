package org.example.walletservice.wallet.application.port.`in`

import org.example.walletservice.wallet.domain.PaymentEventMessage
import org.example.walletservice.wallet.domain.WalletEventMessage

interface SettlementUseCase {

    fun processSettlement(paymentEventMessage: PaymentEventMessage): WalletEventMessage
}