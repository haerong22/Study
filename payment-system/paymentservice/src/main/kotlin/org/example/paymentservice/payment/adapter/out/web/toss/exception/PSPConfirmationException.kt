package org.example.paymentservice.payment.adapter.out.web.toss.exception

import org.example.paymentservice.payment.domain.PaymentStatus

class PSPConfirmationException(
    val errorCode: String,
    val errorMessage: String,
    val isSuccess: Boolean,
    val isFailure: Boolean,
    val isUnknown: Boolean,
    val isRetryableError: Boolean,
    cause: Throwable? = null
) : RuntimeException(errorMessage, cause) {

    init {
        require(isSuccess || isFailure || isUnknown) {
            "${this::class.simpleName} 는 올바르지 않은 결제 상태를 가지고 있습니다."
        }
    }

    fun paymentStatus(): PaymentStatus {
        return when {
            isSuccess -> PaymentStatus.SUCCESS
            isFailure -> PaymentStatus.FAILURE
            isUnknown -> PaymentStatus.UNKNOWN
            else -> error("${this::class.simpleName} 는 올바르지 않은 결제 상태를 가지고 있습니다.")
        }
    }
}