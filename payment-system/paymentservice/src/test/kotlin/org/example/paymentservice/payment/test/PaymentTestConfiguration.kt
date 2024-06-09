package org.example.paymentservice.payment.test

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.transaction.reactive.TransactionalOperator

@TestConfiguration
class PaymentTestConfiguration {

    @Bean
    fun paymentDataBaseHelper(
        databaseClient: DatabaseClient,
        transactionalOperator: TransactionalOperator
    ): PaymentDatabaseHelper {
        return R2DBCPaymentDatabaseHelper(databaseClient, transactionalOperator)
    }
}