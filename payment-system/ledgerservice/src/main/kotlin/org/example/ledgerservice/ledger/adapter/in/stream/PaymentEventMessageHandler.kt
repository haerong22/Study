package org.example.ledgerservice.ledger.adapter.`in`.stream

import org.example.ledgerservice.common.StreamAdapter
import org.example.ledgerservice.ledger.domain.PaymentEventMessage
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import java.util.function.Consumer

@Configuration
@StreamAdapter
class PaymentEventMessageHandler {

    @Bean
    fun consume(): Consumer<Message<PaymentEventMessage>> {
        return Consumer { message ->
            println(message.payload)
        }
    }
}