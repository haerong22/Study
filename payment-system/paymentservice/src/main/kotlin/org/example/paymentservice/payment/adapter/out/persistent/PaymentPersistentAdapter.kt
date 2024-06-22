package org.example.paymentservice.payment.adapter.out.persistent

import org.example.paymentservice.common.PersistentAdapter
import org.example.paymentservice.payment.adapter.out.persistent.repository.PaymentRepository
import org.example.paymentservice.payment.adapter.out.persistent.repository.PaymentStatusUpdateRepository
import org.example.paymentservice.payment.adapter.out.persistent.repository.PaymentValidationRepository
import org.example.paymentservice.payment.application.port.out.LoadPendingPaymentPort
import org.example.paymentservice.payment.application.port.out.PaymentStatusUpdateCommand
import org.example.paymentservice.payment.application.port.out.PaymentStatusUpdatePort
import org.example.paymentservice.payment.application.port.out.PaymentValidationPort
import org.example.paymentservice.payment.application.port.out.SavePaymentPort
import org.example.paymentservice.payment.domain.PaymentEvent
import org.example.paymentservice.payment.domain.PendingPaymentEvent
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@PersistentAdapter
class PaymentPersistentAdapter(
    private val paymentRepository: PaymentRepository,
    private val paymentStatusUpdateRepository: PaymentStatusUpdateRepository,
    private val paymentValidationRepository: PaymentValidationRepository,
) : SavePaymentPort, PaymentStatusUpdatePort, PaymentValidationPort, LoadPendingPaymentPort {

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
}