package org.example.paymentservice.payment.application.port.out

import org.example.paymentservice.payment.domain.PaymentEventMessage
import reactor.core.publisher.Flux

interface LoadPendingPaymentEventMessagePort {

  fun getPendingPaymentEventMessage(): Flux<PaymentEventMessage>
}