package org.example.walletservice.wallet.domain

class PaymentOrder (
    val id: Long,
    val sellerId: Long,
    amount: Long,
    orderId: String
) : Item(
    amount = amount,
    orderId = orderId,
    referenceId = id,
    referenceType = ReferenceType.PAYMENT_ORDER
)
