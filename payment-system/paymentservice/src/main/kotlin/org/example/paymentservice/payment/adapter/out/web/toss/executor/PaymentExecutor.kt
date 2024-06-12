package org.example.paymentservice.payment.adapter.out.web.toss.executor

import org.example.paymentservice.payment.application.port.`in`.PaymentConfirmCommand
import org.example.paymentservice.payment.domain.PaymentExecutionResult
import reactor.core.publisher.Mono

interface PaymentExecutor {

    fun execute(command: PaymentConfirmCommand): Mono<PaymentExecutionResult>
}