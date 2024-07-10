package org.example.paymentservice.payment.domain

data class PaymentOrder(
    val id: Long? = null,
    val paymentEventId: Long? = null,
    val sellerId: Long,
    val productId: Long,
    val orderId: String,
    val amount: Long,
    val paymentStatus: PaymentStatus,
    private val isLedgerUpdated: Boolean = false,
    private val isWalletUpdated: Boolean = false,
) {

    fun isLedgerUpdated(): Boolean = isLedgerUpdated
    fun isWalletUpdated(): Boolean = isWalletUpdated
}