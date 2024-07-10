package org.example.ledgerservice.ledger.adapter.`in`.stream

import org.example.ledgerservice.common.StreamAdapter
import org.example.ledgerservice.ledger.application.port.`in`.DoubleLedgerEntryRecordUseCase
import org.example.ledgerservice.ledger.domain.PaymentEventMessage
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import java.util.function.Consumer

@Configuration
@StreamAdapter
class PaymentEventMessageHandler(
    private val doubleLedgerEntryRecordUseCase: DoubleLedgerEntryRecordUseCase,
    private val streamBridge: StreamBridge,
) {

    @Bean
    fun consume(): Consumer<Message<PaymentEventMessage>> {
        return Consumer { message ->
            println(message.payload)
            val ledgerEventMessage = doubleLedgerEntryRecordUseCase.recordDoubleLedgerEntry(message.payload)
            streamBridge.send("ledger", ledgerEventMessage)
        }
    }
}