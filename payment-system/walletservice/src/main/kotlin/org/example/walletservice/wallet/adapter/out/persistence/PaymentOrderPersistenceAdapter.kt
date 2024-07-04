package org.example.walletservice.wallet.adapter.out.persistence

import org.example.walletservice.common.PersistenceAdapter
import org.example.walletservice.wallet.adapter.out.persistence.repository.PaymentOrderRepository
import org.example.walletservice.wallet.application.port.out.LoadPaymentOrderPort
import org.example.walletservice.wallet.domain.PaymentOrder

@PersistenceAdapter
class PaymentOrderPersistenceAdapter(
    private val paymentOrderRepository: PaymentOrderRepository,
): LoadPaymentOrderPort {

    override fun getPaymentOrders(orderId: String): List<PaymentOrder> {
        return paymentOrderRepository.getPaymentOrders(orderId)
    }
}