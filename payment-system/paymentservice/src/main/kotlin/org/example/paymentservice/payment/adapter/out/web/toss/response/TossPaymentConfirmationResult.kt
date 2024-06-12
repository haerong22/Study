package org.example.paymentservice.payment.adapter.out.web.toss.response

data class TossPaymentConfirmationResponse (
    val version: String,
    val paymentKey: String,
    val type: String,
    val orderId: String,
    val orderName: String,
    val mId: String,
    val currency: String,
    val method: String,
    val totalAmount: Int,
    val balanceAmount: Int,
    val status: String,
    val requestedAt: String,
    val approvedAt: String,
    val useEscrow: Boolean,
    val lastTransactionKey: String?,
    val suppliedAmount: Int,
    val vat: Int,
    val cultureExpense: Boolean,
    val taxFreeAmount: Int,
    val taxExemptionAmount: Int,
    val cancels: List<Cancel>?,
    val card: Card?,
    val virtualAccount: VirtualAccount?,
    val mobilePhone: MobilePhone?,
    val giftCertificate: GiftCertificate?,
    val transfer: Transfer?,
    val receipt: Receipt?,
    val checkout: Checkout?,
    val easyPay: EasyPay?,
    val country: String,
    val failure: TossFailureResponse?,
    val cashReceipt: CashReceipt?,
    val cashReceipts: List<CashReceipt>?,
    val discount: Discount?
)

data class Cancel(
    val cancelAmount: Int,
    val cancelReason: String,
    val taxFreeAmount: Int,
    val taxExemptionAmount: Int,
    val refundableAmount: Int,
    val easyPayDiscountAmount: Int,
    val canceledAt: String,
    val transactionKey: String,
    val receiptKey: String?,
    val isPartialCancelable: Boolean
)

data class Card(
    val amount: Int,
    val issuerCode: String,
    val acquirerCode: String?,
    val number: String,
    val installmentPlanMonths: Int,
    val approveNo: String,
    val useCardPoint: Boolean,
    val cardType: String,
    val ownerType: String,
    val acquireStatus: String,
    val isInterestFree: Boolean,
    val interestPayer: String?
)

data class VirtualAccount(
    val accountType: String,
    val accountNumber: String,
    val bankCode: String,
    val customerName: String,
    val dueDate: String,
    val refundStatus: String,
    val expired: Boolean,
    val settlementStatus: String,
    val refundReceiveAccount: RefundReceiveAccount,
    val secret: String?
)

data class MobilePhone(
    val customerMobilePhone: String,
    val settlementStatus: String,
    val receiptUrl: String
)

data class GiftCertificate(
    val approveNo: String,
    val settlementStatus: String
)

data class Transfer(
    val bankCode: String,
    val settlementStatus: String
)

data class Receipt(
    val url: String
)

data class Checkout(
    val url: String
)

data class EasyPay(
    val provider: String,
    val amount: Int,
    val discountAmount: Int
)

data class TossFailureResponse(
    val code: String,
    val message: String
)

data class CashReceipt(
    val type: String,
    val receiptKey: String,
    val issueNumber: String,
    val receiptUrl: String,
    val amount: Int,
    val taxFreeAmount: Int
)

data class Discount(
    val amount: Int
)

data class RefundReceiveAccount(
    val bankCode: String,
    val accountNumber: String,
    val holderName: String
)