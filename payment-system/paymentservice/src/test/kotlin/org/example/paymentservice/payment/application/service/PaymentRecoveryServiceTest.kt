package org.example.paymentservice.payment.application.service

import io.mockk.every
import io.mockk.mockk
import org.example.paymentservice.payment.adapter.out.web.toss.exception.PSPConfirmationException
import org.example.paymentservice.payment.application.port.`in`.CheckoutCommand
import org.example.paymentservice.payment.application.port.`in`.CheckoutUseCase
import org.example.paymentservice.payment.application.port.`in`.PaymentConfirmCommand
import org.example.paymentservice.payment.application.port.out.LoadPendingPaymentPort
import org.example.paymentservice.payment.application.port.out.PaymentExecutorPort
import org.example.paymentservice.payment.application.port.out.PaymentStatusUpdateCommand
import org.example.paymentservice.payment.application.port.out.PaymentStatusUpdatePort
import org.example.paymentservice.payment.application.port.out.PaymentValidationPort
import org.example.paymentservice.payment.domain.PSPConfirmationStatus
import org.example.paymentservice.payment.domain.PaymentExecutionResult
import org.example.paymentservice.payment.domain.PaymentExtraDetails
import org.example.paymentservice.payment.domain.PaymentFailure
import org.example.paymentservice.payment.domain.PaymentMethod
import org.example.paymentservice.payment.domain.PaymentStatus
import org.example.paymentservice.payment.domain.PaymentType
import org.example.paymentservice.payment.test.PaymentDatabaseHelper
import org.example.paymentservice.payment.test.PaymentTestConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.util.UUID

@SpringBootTest
@Import(PaymentTestConfiguration::class)
class PaymentRecoveryServiceTest(
    @Autowired private val loadPendingPaymentPort: LoadPendingPaymentPort,
    @Autowired private val paymentValidationPort: PaymentValidationPort,
    @Autowired private val paymentStatusUpdatePort: PaymentStatusUpdatePort,
    @Autowired private val checkoutUseCase: CheckoutUseCase,
    @Autowired private val paymentDatabaseHelper: PaymentDatabaseHelper,
    @Autowired private val paymentErrorHandler: PaymentErrorHandler,
) {
    @BeforeEach
    fun clean() {
        paymentDatabaseHelper.clean().block()
    }

    @Test
    fun `should recovery payments`() {
        val paymentConfirmCommand = createUnknownStatusPaymentEvent()
        val paymentExecutionResult = createPaymentExecutionResult(paymentConfirmCommand)

        val mockPaymentExecutorPort = mockk<PaymentExecutorPort>()

        every { mockPaymentExecutorPort.execute(paymentConfirmCommand) } returns Mono.just(paymentExecutionResult)

        val paymentRecoveryService = PaymentRecoveryService(
            loadPendingPaymentPort = loadPendingPaymentPort,
            paymentValidationPort = paymentValidationPort,
            paymentExecutorPort = mockPaymentExecutorPort,
            paymentStatusUpdatePort = paymentStatusUpdatePort,
            paymentErrorHandler = paymentErrorHandler
        )

        paymentRecoveryService.recovery()

        Thread.sleep(10000)
    }

    @Test
    fun `should fail to recovery payment if when an unknown exception occurs`() {
        val paymentConfirmCommand = createUnknownStatusPaymentEvent()
        val paymentExecutionResult = createPaymentExecutionResultWithUnknownStatus(paymentConfirmCommand)

        val mockPaymentExecutorPort = mockk<PaymentExecutorPort>()

        every { mockPaymentExecutorPort.execute(paymentConfirmCommand) } throws PSPConfirmationException(
            errorCode = "UNKNOWN ERROR",
            errorMessage = "test_error_message",
            isSuccess = false,
            isFailure = false,
            isUnknown = true,
            isRetryableError = true,
        )

        val paymentRecoveryService = PaymentRecoveryService(
            loadPendingPaymentPort = loadPendingPaymentPort,
            paymentValidationPort = paymentValidationPort,
            paymentExecutorPort = mockPaymentExecutorPort,
            paymentStatusUpdatePort = paymentStatusUpdatePort,
            paymentErrorHandler = paymentErrorHandler
        )

        paymentRecoveryService.recovery()

        Thread.sleep(10000)
    }

    private fun createUnknownStatusPaymentEvent(): PaymentConfirmCommand {
        val orderId = UUID.randomUUID().toString()
        val paymentKey = UUID.randomUUID().toString()

        val checkoutCommand = CheckoutCommand(
            cartId = 1,
            buyerId = 1,
            productIds = listOf(1, 2),
            idempotencyKey = orderId
        )

        val checkoutResult = checkoutUseCase.checkout(checkoutCommand).block()!!

        val paymentConfirmCommand = PaymentConfirmCommand(
            paymentKey = paymentKey,
            orderId = orderId,
            amount = checkoutResult.amount
        )

        paymentStatusUpdatePort.updatePaymentStatusToExecuting(
            paymentConfirmCommand.orderId,
            paymentConfirmCommand.paymentKey
        ).block()

        val paymentStatusUpdateCommand = PaymentStatusUpdateCommand(
            paymentKey = paymentConfirmCommand.paymentKey,
            orderId = paymentConfirmCommand.orderId,
            status = PaymentStatus.UNKNOWN,
            failure = PaymentFailure("UNKNOWN", "UNKNOWN")
        )

        paymentStatusUpdatePort.updatePaymentStatus(paymentStatusUpdateCommand).block()

        return paymentConfirmCommand
    }

    private fun createPaymentExecutionResult(paymentConfirmCommand: PaymentConfirmCommand): PaymentExecutionResult {
        return PaymentExecutionResult(
            paymentKey = paymentConfirmCommand.paymentKey,
            orderId = paymentConfirmCommand.orderId,
            extraDetails = PaymentExtraDetails(
                type = PaymentType.NORMAL,
                method = PaymentMethod.EASY_PAY,
                totalAmount = paymentConfirmCommand.amount,
                orderName = "test_order_name",
                pspConfirmationStatus = PSPConfirmationStatus.DONE,
                approvedAt = LocalDateTime.now(),
                pspRawData = "{}",
            ),
            isSuccess = true,
            isFailure = false,
            isUnknown = false,
            isRetryable = false
        )
    }

    private fun createPaymentExecutionResultWithUnknownStatus(paymentConfirmCommand: PaymentConfirmCommand): PaymentExecutionResult {
        return PaymentExecutionResult(
            paymentKey = paymentConfirmCommand.paymentKey,
            orderId = paymentConfirmCommand.orderId,
            failure = PaymentFailure(errorCode = "UNKNOWN ERROR", "test_error_message"),
            isSuccess = false,
            isFailure = false,
            isUnknown = true,
            isRetryable = false
        )
    }
}