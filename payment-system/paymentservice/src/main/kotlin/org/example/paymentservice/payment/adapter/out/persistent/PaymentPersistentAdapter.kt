package org.example.paymentservice.payment.adapter.out.persistent

import org.example.paymentservice.common.PersistentAdapter
import org.example.paymentservice.payment.adapter.out.persistent.repository.PaymentRepository
import org.example.paymentservice.payment.application.port.out.SavePaymentPort
import org.example.paymentservice.payment.domain.PaymentEvent
import reactor.core.publisher.Mono

@PersistentAdapter
class PaymentPersistentAdapter(
    private val paymentRepository: PaymentRepository,
) : SavePaymentPort {

    override fun save(paymentEvent: PaymentEvent): Mono<Void> {
        return paymentRepository.save(paymentEvent)
    }
}