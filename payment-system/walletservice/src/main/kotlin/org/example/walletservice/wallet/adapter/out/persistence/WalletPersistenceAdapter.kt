package org.example.walletservice.wallet.adapter.out.persistence

import org.example.walletservice.common.PersistenceAdapter
import org.example.walletservice.wallet.adapter.out.persistence.repository.WalletRepository
import org.example.walletservice.wallet.adapter.out.persistence.repository.WalletTransactionRepository
import org.example.walletservice.wallet.application.port.out.DuplicateMessageFilterPort
import org.example.walletservice.wallet.application.port.out.LoadWalletPort
import org.example.walletservice.wallet.application.port.out.SaveWalletPort
import org.example.walletservice.wallet.domain.PaymentEventMessage
import org.example.walletservice.wallet.domain.Wallet

@PersistenceAdapter
class WalletPersistenceAdapter(
    private val walletTransactionRepository: WalletTransactionRepository,
    private val walletRepository: WalletRepository,
): DuplicateMessageFilterPort, LoadWalletPort, SaveWalletPort {

    override fun isAlreadyProcess(paymentEventMessage: PaymentEventMessage): Boolean {
        return walletTransactionRepository.isExist(paymentEventMessage)
    }

    override fun getWallets(sellerIds: Set<Long>): Set<Wallet> {
        return walletRepository.getWallets(sellerIds)
    }

    override fun save(wallets: List<Wallet>) {
        walletRepository.save(wallets)
    }
}