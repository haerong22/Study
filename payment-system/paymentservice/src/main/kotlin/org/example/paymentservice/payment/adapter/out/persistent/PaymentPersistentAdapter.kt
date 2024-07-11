package org.example.paymentservice.payment.adapter.out.persistent

import org.example.paymentservice.common.PersistentAdapter
import org.example.paymentservice.payment.adapter.out.persistent.repository.PaymentOutboxRepository
import org.example.paymentservice.payment.adapter.out.persistent.repository.PaymentRepository
import org.example.paymentservice.payment.adapter.out.persistent.repository.PaymentStatusUpdateRepository
import org.example.paymentservice.payment.adapter.out.persistent.repository.PaymentValidationRepository
import org.example.paymentservice.payment.application.port.out.CompletePaymentPort
import org.example.paymentservice.payment.application.port.out.LoadPaymentPort
import org.example.paymentservice.payment.application.port.out.LoadPendingPaymentEventMessagePort
import org.example.paymentservice.payment.application.port.out.LoadPendingPaymentPort
import org.example.paymentservice.payment.application.port.out.PaymentStatusUpdateCommand
import org.example.paymentservice.payment.application.port.out.PaymentStatusUpdatePort
import org.example.paymentservice.payment.application.port.out.PaymentValidationPort
import org.example.paymentservice.payment.application.port.out.SavePaymentPort
import org.example.paymentservice.payment.domain.PaymentEvent
import org.example.paymentservice.payment.domain.PaymentEventMessage
import org.example.paymentservice.payment.domain.PendingPaymentEvent
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@PersistentAdapter
class PaymentPersistentAdapter(
    private val paymentRepository: PaymentRepository,
    private val paymentStatusUpdateRepository: PaymentStatusUpdateRepository,
    private val paymentValidationRepository: PaymentValidationRepository,
    private val paymentOutboxRepository: PaymentOutboxRepository,
) : SavePaymentPort, PaymentStatusUpdatePort, PaymentValidationPort, LoadPendingPaymentPort,
    LoadPendingPaymentEventMessagePort, LoadPaymentPort, CompletePaymentPort {

    override fun save(paymentEvent: PaymentEvent): Mono<Void> {
        return paymentRepository.save(paymentEvent)
    }

    override fun updatePaymentStatusToExecuting(orderId: String, paymentKey: String): Mono<Boolean> {
        return paymentStatusUpdateRepository.updatePaymentStatusToExecuting(orderId, paymentKey)
    }

    override fun isValid(orderId: String, amount: Long): Mono<Boolean> {
        return paymentValidationRepository.isValid(orderId, amount)
    }

    override fun updatePaymentStatus(command: PaymentStatusUpdateCommand): Mono<Boolean> {
        return paymentStatusUpdateRepository.updatePaymentStatus(command)
    }

    override fun getPendingPayments(): Flux<PendingPaymentEvent> {
        return paymentRepository.getPendingPayments()
    }

    override fun getPendingPaymentEventMessage(): Flux<PaymentEventMessage> {
        return paymentOutboxRepository.getPendingPaymentOutboxes()
    }

    override fun getPayment(orderId: String): Mono<PaymentEvent> {
        return paymentRepository.getPayment(orderId)
    }

    override fun complete(paymentEvent: PaymentEvent): Mono<Void> {
        return paymentRepository.complete(paymentEvent)
    }
}