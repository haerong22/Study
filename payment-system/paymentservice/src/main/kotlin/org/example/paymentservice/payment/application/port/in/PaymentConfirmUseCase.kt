package org.example.paymentservice.payment.application.port.`in`

import org.example.paymentservice.payment.domain.PaymentConfirmResult
import reactor.core.publisher.Mono

interface PaymentConfirmUseCase {

    fun confirm(command: PaymentConfirmCommand): Mono<PaymentConfirmResult>
}