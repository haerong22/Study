package org.example.paymentservice.payment.adapter.`in`.stream

import org.example.paymentservice.common.StreamAdapter
import org.example.paymentservice.payment.application.port.`in`.PaymentCompleteUseCase
import org.example.paymentservice.payment.domain.WalletEventMessage
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.Message
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kafka.receiver.ReceiverOffset
import java.util.function.Function

@Configuration
@StreamAdapter
class WalletEventMessageHandler(
    private val paymentCompleteUseCase: PaymentCompleteUseCase,
) {

    @Bean
    fun wallet(): Function<Flux<Message<WalletEventMessage>>, Mono<Void>> {
        return Function { flux ->
            flux.flatMap { message ->
                paymentCompleteUseCase.completePayment(message.payload)
                    .then(Mono.defer {
                        message.headers.get(KafkaHeaders.ACKNOWLEDGMENT, ReceiverOffset::class.java)!!.commit()
                    })
            }.then()
        }
    }
}