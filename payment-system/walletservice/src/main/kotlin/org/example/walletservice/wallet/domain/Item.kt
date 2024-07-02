package org.example.walletservice.wallet.domain

open class Item (
  val amount: Long,
  val orderId: String,
  val referenceId: Long,
  val referenceType: ReferenceType
)

enum class ReferenceType {
  PAYMENT_ORDER
}