package org.example.ledgerservice.ledger.domain

class PaymentOrder (
  id: Long,
  amount: Long,
  orderId: String
) : Item (
  id = id,
  amount = amount,
  orderId = orderId,
  type = ReferenceType.PAYMENT_ORDER
)