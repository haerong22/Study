package org.example.paymentservice.payment.domain

data class WalletEventMessage (
  val type: WalletEventMessageType,
  val payload: Map<String, Any?> = emptyMap(),
  val metadata: Map<String, Any?> = emptyMap(),
) {
  fun orderId(): String {
    return payload["orderId"] as String
  }
}

enum class WalletEventMessageType (description: String) {
  SUCCESS("정산 처리 성공")
}