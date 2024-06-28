package org.example.paymentservice.payment.adapter.out.persistent.repository

import org.example.paymentservice.payment.application.port.out.PaymentStatusUpdateCommand
import org.example.paymentservice.payment.domain.PaymentEventMessage
import org.example.paymentservice.payment.domain.PaymentEventMessageType
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PaymentOutboxRepository {

    fun insertOutbox(command: PaymentStatusUpdateCommand): Mono<PaymentEventMessage>

    fun markMessageAsSent(idempotencyKey: String, type: PaymentEventMessageType): Mono<Boolean>

    fun markMessageAsFailure(idempotencyKey: String, type: PaymentEventMessageType): Mono<Boolean>

    fun getPendingPaymentOutboxes(): Flux<PaymentEventMessage>
}