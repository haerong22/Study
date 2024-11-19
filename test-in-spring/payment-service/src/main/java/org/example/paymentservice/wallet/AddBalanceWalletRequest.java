package org.example.paymentservice.wallet;

import java.math.BigDecimal;

public record AddBalanceWalletRequest(
        Long walletId,
        BigDecimal amount
) {
}
