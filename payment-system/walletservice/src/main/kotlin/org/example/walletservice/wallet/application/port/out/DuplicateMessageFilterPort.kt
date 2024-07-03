package org.example.walletservice.wallet.application.port.out

import org.example.walletservice.wallet.domain.PaymentEventMessage

interface DuplicateMessageFilterPort {

    fun isAlreadyProcess(paymentEventMessage: PaymentEventMessage): Boolean
}