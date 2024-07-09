package org.example.ledgerservice.ledger.application.port.out

import org.example.ledgerservice.ledger.domain.PaymentOrder


interface LoadPaymentOrderPort {

  fun getPaymentOrders(orderId: String): List<PaymentOrder>
}