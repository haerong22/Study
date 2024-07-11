package org.example.paymentservice.payment.application.port.out

import org.example.paymentservice.payment.domain.PaymentEvent
import reactor.core.publisher.Mono

interface CompletePaymentPort {

  fun complete(paymentEvent: PaymentEvent): Mono<Void>
}