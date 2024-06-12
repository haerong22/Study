package org.example.paymentservice.payment.adapter.out.web.toss

import org.example.paymentservice.common.WebAdapter
import org.example.paymentservice.payment.adapter.out.web.toss.executor.PaymentExecutor
import org.example.paymentservice.payment.application.port.`in`.PaymentConfirmCommand
import org.example.paymentservice.payment.application.port.out.PaymentExecutorPort
import org.example.paymentservice.payment.domain.PaymentExecutionResult
import reactor.core.publisher.Mono

@WebAdapter
class PaymentExecutorWebAdaptor(
    private val paymentExecutor: PaymentExecutor,
): PaymentExecutorPort {

    override fun execute(command: PaymentConfirmCommand): Mono<PaymentExecutionResult> {
        return paymentExecutor.execute(command)
    }
}