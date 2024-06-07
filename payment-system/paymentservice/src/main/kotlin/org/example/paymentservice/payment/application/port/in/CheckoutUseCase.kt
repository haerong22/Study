package org.example.paymentservice.payment.application.port.`in`

import org.example.paymentservice.payment.domain.CheckoutResult
import reactor.core.publisher.Mono

interface CheckoutUseCase {

    fun checkout(command: CheckoutCommand): Mono<CheckoutResult>
}