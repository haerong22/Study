package org.example.paymentservice.payment.application.port.out

import org.example.paymentservice.payment.domain.PendingPaymentEvent
import reactor.core.publisher.Flux

interface LoadPendingPaymentPort {

    fun getPendingPayments(): Flux<PendingPaymentEvent>
}