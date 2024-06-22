package org.example.paymentservice.payment.domain

data class PendingPaymentOrder (
  val paymentOrderId: Long,
  val status: PaymentStatus,
  val amount: Long,
  val failedCount: Byte,
  val threshold: Byte
)
