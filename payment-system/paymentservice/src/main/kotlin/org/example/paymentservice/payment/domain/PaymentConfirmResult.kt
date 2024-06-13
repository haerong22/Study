package org.example.paymentservice.payment.domain

data class PaymentConfirmResult(
    val status: PaymentStatus,
    val failure: PaymentFailure? = null
) {
    init {
        if (status == PaymentStatus.FAILURE) {
            requireNotNull(failure) {
                "결제 상태 FAILURE 일 때 PaymentExecutionFailure 는 null 값이 될 수 없습니다."
            }
        }
    }

    val message = when (status) {
        PaymentStatus.SUCCESS -> "결제 처리에 성공하였습니다."
        PaymentStatus.FAILURE -> "결제 처리에 실패하였습니다."
        PaymentStatus.UNKNOWN -> "결제 처리 중에 알 수 없는 에러가 발생하였습니다."
        else -> error("현재 결제 상태 (status: $status) 는 올바르지 않은 상태입니다. ")
    }
}
