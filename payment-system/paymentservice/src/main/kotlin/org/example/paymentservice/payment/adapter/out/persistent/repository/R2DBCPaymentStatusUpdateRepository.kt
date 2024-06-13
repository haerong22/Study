package org.example.paymentservice.payment.adapter.out.persistent.repository

import org.example.paymentservice.payment.adapter.out.persistent.exception.PaymentAlreadyProcessException
import org.example.paymentservice.payment.application.port.out.PaymentStatusUpdateCommand
import org.example.paymentservice.payment.domain.PaymentStatus
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class R2DBCPaymentStatusUpdateRepository(
    private val databaseClient: DatabaseClient,
    private val transactionalOperator: TransactionalOperator,
) : PaymentStatusUpdateRepository {

    override fun updatePaymentStatusToExecuting(orderId: String, paymentKey: String): Mono<Boolean> {
        return checkPreviousPaymentOrderStatus(orderId)
            .flatMap { insertPaymentHistory(it, PaymentStatus.EXECUTING, "PAYMENT_CONFIRMATION_START") }
            .flatMap { updatePaymentOrderStatus(orderId, PaymentStatus.EXECUTING) }
            .flatMap { updatePaymentKey(orderId, paymentKey) }
            .`as`(transactionalOperator::transactional)
            .thenReturn(true)
    }

    override fun updatePaymentStatus(command: PaymentStatusUpdateCommand): Mono<Boolean> {
        return when (command.status) {
            PaymentStatus.SUCCESS -> updatePaymentStatusToSuccess(command)
            PaymentStatus.FAILURE -> updatePaymentStatusToFailure(command)
            PaymentStatus.UNKNOWN -> updatePaymentStatusToUnknown(command)
            else -> error("결제 상태 (status: ${command.status}) 는 올바르지 않은 결제 상태입니다.")
        }
    }

    private fun checkPreviousPaymentOrderStatus(orderId: String): Mono<List<Pair<Long, String>>> {
        return selectPaymentOrderStatus(orderId)
            .handle { paymentOrder, sink ->
                when (paymentOrder.second) {
                    PaymentStatus.NOT_STARTED.name, PaymentStatus.UNKNOWN.name, PaymentStatus.EXECUTING.name -> {
                        sink.next(paymentOrder)
                    }

                    PaymentStatus.SUCCESS.name -> {
                        sink.error(
                            PaymentAlreadyProcessException(
                                message = "이미 처리 성공한 결제 입니다.",
                                status = PaymentStatus.SUCCESS
                            )
                        )
                    }

                    PaymentStatus.FAILURE.name -> {
                        sink.error(
                            PaymentAlreadyProcessException(
                                message = "이미 처리 실패한 결제 입니다.",
                                status = PaymentStatus.FAILURE
                            )
                        )
                    }
                }
            }
            .collectList()
    }

    private fun selectPaymentOrderStatus(orderId: String): Flux<Pair<Long, String>> {
        return databaseClient.sql(SELECT_PAYMENT_ORDER_STATUS_QUERY)
            .bind("orderId", orderId)
            .fetch()
            .all()
            .map { Pair(it["id"] as Long, it["payment_order_status"] as String) }
    }

    private fun insertPaymentHistory(
        paymentOrderIdToStatus: List<Pair<Long, String>>,
        status: PaymentStatus,
        reason: String
    ): Mono<Long> {
        if (paymentOrderIdToStatus.isEmpty()) return Mono.empty()

        val valueClauses = paymentOrderIdToStatus.joinToString(", ") {
            "( ${it.first}, '${it.second}', '${status}', '${reason}' )"
        }

        return databaseClient.sql(INSERT_PAYMENT_HISTORY_QUERY(valueClauses))
            .fetch()
            .rowsUpdated()
    }

    private fun updatePaymentOrderStatus(orderId: String, status: PaymentStatus): Mono<Long> {
        return databaseClient.sql(UPDATE_PAYMENT_ORDER_STATUS_QUERY)
            .bind("orderId", orderId)
            .bind("status", status)
            .fetch()
            .rowsUpdated()
    }

    private fun updatePaymentKey(orderId: String, paymentKey: String): Mono<Long> {
        return databaseClient.sql(UPDATE_PAYMENT_KEY_QUERY)
            .bind("orderId", orderId)
            .bind("paymentKey", paymentKey)
            .fetch()
            .rowsUpdated()
    }

    private fun updatePaymentStatusToSuccess(command: PaymentStatusUpdateCommand): Mono<Boolean> {
        return selectPaymentOrderStatus(command.orderId)
            .collectList()
            .flatMap { insertPaymentHistory(it, command.status, "PAYMENT_CONFIRMATION_DONE") }
            .flatMap { updatePaymentOrderStatus(command.orderId, command.status) }
            .flatMap { updatePaymentEventExtraDetails(command) }
            .`as`(transactionalOperator::transactional)
            .thenReturn(true)
    }

    private fun updatePaymentStatusToFailure(command: PaymentStatusUpdateCommand): Mono<Boolean> {
        return selectPaymentOrderStatus(command.orderId)
            .collectList()
            .flatMap { insertPaymentHistory(it, command.status, command.failure.toString()) }
            .flatMap { updatePaymentOrderStatus(command.orderId, command.status) }
            .`as`(transactionalOperator::transactional)
            .thenReturn(true)
    }

    private fun updatePaymentStatusToUnknown(command: PaymentStatusUpdateCommand): Mono<Boolean> {
        return selectPaymentOrderStatus(command.orderId)
            .collectList()
            .flatMap { insertPaymentHistory(it, command.status, command.failure.toString()) }
            .flatMap { updatePaymentOrderStatus(command.orderId, command.status) }
            .flatMap { incrementPaymentOrderFailedCount(command) }
            .`as`(transactionalOperator::transactional)
            .thenReturn(true)
    }

    private fun updatePaymentEventExtraDetails(command: PaymentStatusUpdateCommand): Mono<Long> {
        return databaseClient.sql(UPDATE_PAYMENT_EVENT_EXTRA_DETAILS_QUERY)
            .bind("orderName", command.extraDetails!!.orderName)
            .bind("method", command.extraDetails.method.name)
            .bind("approvedAt", command.extraDetails.approvedAt.toString())
            .bind("orderId", command.orderId)
            .bind("type", command.extraDetails.type)
            .fetch()
            .rowsUpdated()
    }

    private fun incrementPaymentOrderFailedCount(command: PaymentStatusUpdateCommand): Mono<Long> {
        return databaseClient.sql(INCREMENT_PAYMENT_ORDER_FAILED_COUNT_QUERY)
            .bind("orderId", command.orderId)
            .fetch()
            .rowsUpdated()
    }

    companion object {
        val SELECT_PAYMENT_ORDER_STATUS_QUERY = """
            SELECT id, payment_order_status
            FROM payment_orders
            WHERE order_id = :orderId
        """.trimIndent()

        val INSERT_PAYMENT_HISTORY_QUERY = fun(valueClauses: String) = """
            INSERT INTO payment_order_histories (payment_order_id, previous_status, new_status, reason) 
            VALUES $valueClauses
        """.trimIndent()

        val UPDATE_PAYMENT_ORDER_STATUS_QUERY = """
            UPDATE payment_orders 
            SET payment_order_status = :status, updated_at = CURRENT_TIMESTAMP
            WHERE order_id = :orderId
        """.trimIndent()

        val UPDATE_PAYMENT_KEY_QUERY = """
            UPDATE payment_events
            SET payment_key = :paymentKey
            WHERE order_id = :orderId
        """.trimIndent()

        val UPDATE_PAYMENT_EVENT_EXTRA_DETAILS_QUERY = """
            UPDATE payment_events
            SET order_name = :orderName, method = :method, approved_at = :approvedAt, type = :type, updated_at = CURRENT_TIMESTAMP
            WHERE order_id = :orderId
        """.trimIndent()

        val INCREMENT_PAYMENT_ORDER_FAILED_COUNT_QUERY = """
            UPDATE payment_orders
            SET failed_count = failed_count + 1
            WHERE order_id = :orderId
        """.trimIndent()
    }
}