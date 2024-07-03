package org.example.walletservice.wallet.application.port.out

import org.example.walletservice.wallet.domain.PaymentOrder

interface LoadPaymentOrderPort {

    fun getPaymentOrders(orderId: String): List<PaymentOrder>
}