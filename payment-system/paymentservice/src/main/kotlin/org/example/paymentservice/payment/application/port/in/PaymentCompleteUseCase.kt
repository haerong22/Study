package org.example.paymentservice.payment.application.port.`in`

import org.example.paymentservice.payment.domain.LedgerEventMessage
import org.example.paymentservice.payment.domain.WalletEventMessage
import reactor.core.publisher.Mono

interface PaymentCompleteUseCase {

    fun completePayment(walletEventMessage: WalletEventMessage): Mono<Void>

    fun completePayment(ledgerEventMessage: LedgerEventMessage): Mono<Void>
}