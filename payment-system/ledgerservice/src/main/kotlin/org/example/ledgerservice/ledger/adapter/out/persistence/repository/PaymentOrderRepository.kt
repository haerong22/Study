package org.example.ledgerservice.ledger.adapter.out.persistence.repository

import org.example.ledgerservice.ledger.domain.PaymentOrder

interface PaymentOrderRepository {

  fun getPaymentOrders(orderId: String): List<PaymentOrder>
}