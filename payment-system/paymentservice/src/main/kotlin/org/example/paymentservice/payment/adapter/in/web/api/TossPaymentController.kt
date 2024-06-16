package org.example.paymentservice.payment.adapter.`in`.web.api

import org.example.paymentservice.common.WebAdapter
import org.example.paymentservice.payment.adapter.`in`.web.request.TossPaymentConfirmRequest
import org.example.paymentservice.payment.adapter.`in`.web.response.ApiResponse
import org.example.paymentservice.payment.application.port.`in`.PaymentConfirmCommand
import org.example.paymentservice.payment.application.port.`in`.PaymentConfirmUseCase
import org.example.paymentservice.payment.domain.PaymentConfirmResult
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@WebAdapter
@RequestMapping("/v1/toss")
@RestController
class TossPaymentController(
    private val paymentConfirmUseCase: PaymentConfirmUseCase,
) {

    @PostMapping("/confirm")
    fun confirm(
        @RequestBody request: TossPaymentConfirmRequest
    ): Mono<ResponseEntity<ApiResponse<PaymentConfirmResult>>> {
        val command = PaymentConfirmCommand(
            paymentKey = request.paymentKey,
            orderId = request.orderId,
            amount = request.amount,
        )

        return paymentConfirmUseCase.confirm(command)
            .map { ResponseEntity.ok().body(ApiResponse.with(HttpStatus.OK, "", it)) }
    }
}