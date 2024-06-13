package org.example.paymentservice.payment.adapter.out.persistent.repository

import org.example.paymentservice.payment.application.port.out.PaymentStatusUpdateCommand
import reactor.core.publisher.Mono

interface PaymentStatusUpdateRepository {

    fun updatePaymentStatusToExecuting(orderId: String, paymentKey: String): Mono<Boolean>

    fun updatePaymentStatus(command: PaymentStatusUpdateCommand): Mono<Boolean>
}