package org.example.walletservice.wallet.application.port.out

import org.example.walletservice.wallet.domain.Wallet

interface LoadWalletPort {

    fun getWallets(sellerIds: Set<Long>): Set<Wallet>
}