package org.example.paymentservice.payment.adapter.out.stream

import org.example.paymentservice.payment.domain.PaymentEventMessage
import org.example.paymentservice.payment.domain.PaymentEventMessageType
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.UUID

@SpringBootTest
@Tag("ExternalIntegration")
class PaymentEventMessageSenderTest(
    @Autowired private val paymentEventMessageSender: PaymentEventMessageSender,
) {

    @Test
    fun `should send eventMessage by using partitionKey`() {
        val paymentEventMessages = listOf(
            PaymentEventMessage(
                type = PaymentEventMessageType.PAYMENT_CONFIRMATION_SUCCESS,
                payload = mapOf(
                    "orderId" to UUID.randomUUID().toString(),
                ),
                metadata = mapOf(
                    "partitionKey" to 0
                )
            ),
            PaymentEventMessage(
                type = PaymentEventMessageType.PAYMENT_CONFIRMATION_SUCCESS,
                payload = mapOf(
                    "orderId" to UUID.randomUUID().toString(),
                ),
                metadata = mapOf(
                    "partitionKey" to 1
                )
            ),
            PaymentEventMessage(
                type = PaymentEventMessageType.PAYMENT_CONFIRMATION_SUCCESS,
                payload = mapOf(
                    "orderId" to UUID.randomUUID().toString(),
                ),
                metadata = mapOf(
                    "partitionKey" to 2
                )
            ),
        )

        paymentEventMessages.forEach {
            paymentEventMessageSender.dispatch(it)
        }

        Thread.sleep(10000)
    }
}