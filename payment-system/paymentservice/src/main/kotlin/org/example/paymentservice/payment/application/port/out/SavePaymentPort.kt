package org.example.paymentservice.payment.application.port.out

import org.example.paymentservice.payment.domain.PaymentEvent
import reactor.core.publisher.Mono

interface SavePaymentPort {

    fun save(paymentEvent: PaymentEvent): Mono<Void>
}