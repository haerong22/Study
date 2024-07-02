package org.example.walletservice.wallet.domain

import java.math.BigDecimal

data class Wallet (
  val id: Long,
  val userId: Long,
  val version: Int,
  val balance: BigDecimal,
  val walletTransactions: List<WalletTransaction> = emptyList()
) {

  fun calculateBalanceWith(items: List<Item>): Wallet {
    return copy(
      balance = balance + BigDecimal(items.sumOf { it.amount }),
      walletTransactions = items.map {
        WalletTransaction(
          walletId = this.id,
          amount = it.amount,
          type = TransactionType.CREDIT,
          referenceId = it.referenceId,
          referenceType = it.referenceType,
          orderId = it.orderId
        )
      }
    )
  }
}