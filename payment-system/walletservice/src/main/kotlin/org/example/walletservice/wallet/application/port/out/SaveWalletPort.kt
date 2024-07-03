package org.example.walletservice.wallet.application.port.out

import org.example.walletservice.wallet.domain.Wallet

interface SaveWalletPort {

    fun save(wallets: List<Wallet>)
}