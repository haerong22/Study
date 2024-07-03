package org.example.walletservice.wallet.adapter.out.persistence.repository

import org.example.walletservice.wallet.domain.PaymentOrder

interface PaymentOrderRepository {

    fun getPaymentOrders(orderId: String): List<PaymentOrder>
}