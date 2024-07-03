package org.example.walletservice.wallet.application.service

import org.example.walletservice.common.UseCase
import org.example.walletservice.wallet.application.port.`in`.SettlementUseCase
import org.example.walletservice.wallet.application.port.out.DuplicateMessageFilterPort
import org.example.walletservice.wallet.application.port.out.LoadPaymentOrderPort
import org.example.walletservice.wallet.application.port.out.LoadWalletPort
import org.example.walletservice.wallet.application.port.out.SaveWalletPort
import org.example.walletservice.wallet.domain.PaymentEventMessage
import org.example.walletservice.wallet.domain.PaymentOrder
import org.example.walletservice.wallet.domain.Wallet
import org.example.walletservice.wallet.domain.WalletEventMessage
import org.example.walletservice.wallet.domain.WalletEventMessageType

@UseCase
class SettlementService(
    private val duplicateMessageFilterPort: DuplicateMessageFilterPort,
    private val loadPaymentOrderPort: LoadPaymentOrderPort,
    private val loadWalletPort: LoadWalletPort,
    private val saveWalletPort: SaveWalletPort,
) : SettlementUseCase {

    override fun processSettlement(paymentEventMessage: PaymentEventMessage): WalletEventMessage {
        if (duplicateMessageFilterPort.isAlreadyProcess(paymentEventMessage)) {
            return createWalletEventMessage(paymentEventMessage)
        }

        val paymentOrders = loadPaymentOrderPort.getPaymentOrders(paymentEventMessage.orderId())
        val paymentOrderBySellerId = paymentOrders.groupBy { it.sellerId }
        val updatedWallets = getUpdatedWallets(paymentOrderBySellerId)

        saveWalletPort.save(updatedWallets)

        return createWalletEventMessage(paymentEventMessage)
    }

    private fun createWalletEventMessage(paymentEventMessage: PaymentEventMessage) =
        WalletEventMessage(
            type = WalletEventMessageType.SUCCESS,
            payload = mapOf(
                "orderId" to paymentEventMessage.orderId()
            )
        )

    private fun getUpdatedWallets(paymentOrdersBySellerId: Map<Long, List<PaymentOrder>>): List<Wallet> {
        val sellerIds = paymentOrdersBySellerId.keys

        val wallets = loadWalletPort.getWallets(sellerIds)

        return wallets.map { wallet ->
            wallet.calculateBalanceWith(paymentOrdersBySellerId[wallet.userId]!!)
        }
    }
}