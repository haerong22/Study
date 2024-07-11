package org.example.paymentservice.payment.domain

data class LedgerEventMessage (
  val type: LedgerEventMessageType,
  val payload: Map<String, Any?> = emptyMap(),
  val metadata: Map<String, Any?> = emptyMap()
) {
  fun orderId(): String {
    return payload["orderId"] as String
  }
}

enum class LedgerEventMessageType (description: String) {
  SUCCESS("장부 기입 성공")
}