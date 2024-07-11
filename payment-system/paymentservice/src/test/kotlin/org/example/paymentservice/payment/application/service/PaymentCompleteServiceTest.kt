package org.example.paymentservice.payment.application.service

import io.mockk.every
import io.mockk.mockk
import org.example.paymentservice.payment.application.port.`in`.CheckoutCommand
import org.example.paymentservice.payment.application.port.`in`.CheckoutUseCase
import org.example.paymentservice.payment.application.port.`in`.PaymentCompleteUseCase
import org.example.paymentservice.payment.application.port.`in`.PaymentConfirmCommand
import org.example.paymentservice.payment.application.port.out.PaymentExecutorPort
import org.example.paymentservice.payment.application.port.out.PaymentStatusUpdatePort
import org.example.paymentservice.payment.application.port.out.PaymentValidationPort
import org.example.paymentservice.payment.domain.LedgerEventMessage
import org.example.paymentservice.payment.domain.LedgerEventMessageType
import org.example.paymentservice.payment.domain.PSPConfirmationStatus
import org.example.paymentservice.payment.domain.PaymentExecutionResult
import org.example.paymentservice.payment.domain.PaymentExtraDetails
import org.example.paymentservice.payment.domain.PaymentMethod
import org.example.paymentservice.payment.domain.PaymentType
import org.example.paymentservice.payment.domain.WalletEventMessage
import org.example.paymentservice.payment.domain.WalletEventMessageType
import org.example.paymentservice.payment.test.PaymentDatabaseHelper
import org.example.paymentservice.payment.test.PaymentTestConfiguration
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import reactor.core.publisher.Hooks
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.util.UUID

@SpringBootTest
@Import(PaymentTestConfiguration::class)
class PaymentCompleteServiceTest (
    @Autowired private val paymentDatabaseHelper: PaymentDatabaseHelper,
    @Autowired private val checkoutUseCase: CheckoutUseCase,
    @Autowired private val paymentStatusUpdatePort: PaymentStatusUpdatePort,
    @Autowired private val paymentValidationPort: PaymentValidationPort,
    @Autowired private val paymentCompleteUseCase: PaymentCompleteUseCase,
    @Autowired private val paymentErrorHandler: PaymentErrorHandler,
) {

    private val mockPaymentExecutorPort = mockk<PaymentExecutorPort>()

    @BeforeEach
    fun clean() {
        paymentDatabaseHelper.clean().block()
    }

    @Test
    fun `should update payment given a WalletEventMessage`() {
        Hooks.onOperatorDebug()

        val orderId = createPaymentEventWithSuccessStatus()

        val walletEventMessage = WalletEventMessage(
            type = WalletEventMessageType.SUCCESS,
            payload = mapOf(
                "orderId" to orderId
            )
        )

        paymentCompleteUseCase.completePayment(walletEventMessage).block()

        val paymentEvent = paymentDatabaseHelper.getPayments(orderId)!!

        assertTrue(paymentEvent.isWalletUpdateDone())
        assertFalse(paymentEvent.isLedgerUpdateDone())
        assertFalse(paymentEvent.isPaymentDone())
    }

    @Test
    fun `should update payment given a LedgerEventMessage`() {
        Hooks.onOperatorDebug()

        val orderId = createPaymentEventWithSuccessStatus()

        val ledgerEventMessage = LedgerEventMessage(
            type = LedgerEventMessageType.SUCCESS,
            payload = mapOf(
                "orderId" to orderId
            )
        )

        paymentCompleteUseCase.completePayment(ledgerEventMessage).block()

        val paymentEvent = paymentDatabaseHelper.getPayments(orderId)!!

        assertTrue(paymentEvent.isLedgerUpdateDone())
        assertFalse(paymentEvent.isWalletUpdateDone())
        assertFalse(paymentEvent.isPaymentDone())
    }

    @Test
    fun `should update payment given a LedgerEventMessage and WalletEventMessage`() {
        Hooks.onOperatorDebug()

        val orderId = createPaymentEventWithSuccessStatus()

        val ledgerEventMessage = LedgerEventMessage(
            type = LedgerEventMessageType.SUCCESS,
            payload = mapOf(
                "orderId" to orderId
            )
        )
        val walletEventMessage = WalletEventMessage(
            type = WalletEventMessageType.SUCCESS,
            payload = mapOf(
                "orderId" to orderId
            )
        )

        paymentCompleteUseCase.completePayment(ledgerEventMessage).block()
        paymentCompleteUseCase.completePayment(walletEventMessage).block()

        val paymentEvent = paymentDatabaseHelper.getPayments(orderId)!!

        assertTrue(paymentEvent.isPaymentDone())
        assertTrue(paymentEvent.isWalletUpdateDone())
        assertTrue(paymentEvent.isLedgerUpdateDone())
    }

    private fun createPaymentEventWithSuccessStatus(): String {
        val orderId = UUID.randomUUID().toString()

        val checkoutCommand = CheckoutCommand(
            cartId = 1,
            buyerId = 1,
            productIds = listOf(1, 2, 3),
            idempotencyKey = orderId
        )

        val checkoutResult = checkoutUseCase.checkout(checkoutCommand).block()!!

        val paymentConfirmCommand = PaymentConfirmCommand(
            paymentKey = UUID.randomUUID().toString(),
            orderId = orderId,
            amount = checkoutResult.amount
        )

        val paymentConfirmService = PaymentConfirmService(
            paymentStatusUpdatePort = paymentStatusUpdatePort,
            paymentValidationPort = paymentValidationPort,
            paymentExecutorPort = mockPaymentExecutorPort,
            paymentErrorHandler = paymentErrorHandler
        )

        val paymentExecutionResult = PaymentExecutionResult(
            paymentKey = paymentConfirmCommand.paymentKey,
            orderId = paymentConfirmCommand.orderId,
            extraDetails = PaymentExtraDetails(
                type = PaymentType.NORMAL,
                method = PaymentMethod.EASY_PAY,
                totalAmount = paymentConfirmCommand.amount,
                orderName = "test_order_name",
                pspConfirmationStatus = PSPConfirmationStatus.DONE,
                approvedAt = LocalDateTime.now(),
                pspRawData = "{}"
            ),
            isSuccess = true,
            isRetryable = false,
            isUnknown = false,
            isFailure = false
        )

        every { mockPaymentExecutorPort.execute(paymentConfirmCommand) } returns Mono.just(paymentExecutionResult)

        paymentConfirmService.confirm(paymentConfirmCommand).block()!!

        return orderId
    }
}