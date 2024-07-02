package org.example.walletservice.wallet.adapter.out.persistence.repository

import org.example.walletservice.wallet.domain.Wallet

interface WalletRepository {

    fun getWallets(sellerIds: Set<Long>): Set<Wallet>

    fun save(wallets: List<Wallet>)
}