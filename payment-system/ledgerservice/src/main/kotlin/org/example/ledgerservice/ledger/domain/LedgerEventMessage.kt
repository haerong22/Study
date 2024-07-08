package org.example.ledgerservice.ledger.domain

data class LedgerEventMessage (
  val type: LedgerEventMessageType,
  val payload: Map<String, Any?> = emptyMap(),
  val metadata: Map<String, Any?> = emptyMap()
)

enum class LedgerEventMessageType(description: String) {
  SUCCESS("장부 기입 성공")
}