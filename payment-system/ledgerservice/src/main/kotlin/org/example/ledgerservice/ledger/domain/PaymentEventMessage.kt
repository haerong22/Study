package org.example.ledgerservice.ledger.domain

data class PaymentEventMessage(
    val type: PaymentEventMessageType,
    val payload: Map<String, Any?> = emptyMap(),
    val metadata: Map<String, Any?> = emptyMap(),
)

enum class PaymentEventMessageType(description: String) {
    PAYMENT_CONFIRMATION_SUCCESS("결제 승인 성공"),
}
