package org.example.paymentservice.payment.application.service

import org.example.paymentservice.common.UseCase
import org.example.paymentservice.payment.application.port.`in`.PaymentCompleteUseCase
import org.example.paymentservice.payment.application.port.out.CompletePaymentPort
import org.example.paymentservice.payment.application.port.out.LoadPaymentPort
import org.example.paymentservice.payment.domain.LedgerEventMessage
import org.example.paymentservice.payment.domain.WalletEventMessage
import reactor.core.publisher.Mono

@UseCase
class PaymentCompleteService (
  private val loadPaymentPort: LoadPaymentPort,
  private val completePaymentPort: CompletePaymentPort
) : PaymentCompleteUseCase {

  override fun completePayment(walletEventMessage: WalletEventMessage): Mono<Void> {
    return loadPaymentPort.getPayment(walletEventMessage.orderId())
      .map { it.apply { confirmWalletUpdate() } }
      .map { it.apply { completeIfDone() } }
      .flatMap { completePaymentPort.complete(it) }
  }

  override fun completePayment(ledgerEventMessage: LedgerEventMessage): Mono<Void> {
    return loadPaymentPort.getPayment(ledgerEventMessage.orderId())
      .map { it.apply { confirmLedgerUpdate() } }
      .map { it.apply { completeIfDone() } }
      .flatMap { completePaymentPort.complete(it) }
  }
}