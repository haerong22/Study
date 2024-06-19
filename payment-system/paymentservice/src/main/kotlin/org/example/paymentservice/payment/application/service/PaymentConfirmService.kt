package org.example.paymentservice.payment.application.service

import org.example.paymentservice.common.UseCase
import org.example.paymentservice.payment.application.port.`in`.PaymentConfirmCommand
import org.example.paymentservice.payment.application.port.`in`.PaymentConfirmUseCase
import org.example.paymentservice.payment.application.port.out.PaymentExecutorPort
import org.example.paymentservice.payment.application.port.out.PaymentStatusUpdateCommand
import org.example.paymentservice.payment.application.port.out.PaymentStatusUpdatePort
import org.example.paymentservice.payment.application.port.out.PaymentValidationPort
import org.example.paymentservice.payment.domain.PaymentConfirmResult
import reactor.core.publisher.Mono

@UseCase
class PaymentConfirmService(
    private val paymentStatusUpdatePort: PaymentStatusUpdatePort,
    private val paymentValidationPort: PaymentValidationPort,
    private val paymentExecutorPort: PaymentExecutorPort,
    private val paymentErrorHandler: PaymentErrorHandler,
) : PaymentConfirmUseCase {

    override fun confirm(command: PaymentConfirmCommand): Mono<PaymentConfirmResult> {
        return paymentStatusUpdatePort.updatePaymentStatusToExecuting(command.orderId, command.paymentKey)
            .filterWhen { paymentValidationPort.isValid(command.orderId, command.amount) }
            .flatMap { paymentExecutorPort.execute(command) }
            .flatMap {
                paymentStatusUpdatePort.updatePaymentStatus(
                    command = PaymentStatusUpdateCommand(
                        paymentKey = it.paymentKey,
                        orderId = it.orderId,
                        status = it.paymentStatus(),
                        extraDetails = it.extraDetails,
                        failure = it.failure,
                    )
                ).thenReturn(it)
            }
            .map {
                PaymentConfirmResult(
                    status = it.paymentStatus(),
                    failure = it.failure,
                )
            }
            .onErrorResume { paymentErrorHandler.handlePaymentConfirmationError(it, command) }
    }
}