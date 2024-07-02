package org.example.walletservice.wallet.adapter.`in`.stream

import org.example.walletservice.common.StreamAdapter
import org.example.walletservice.wallet.domain.PaymentEventMessage
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