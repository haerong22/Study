package org.example.paymentservice.wallet;

import java.math.BigDecimal;

public record AddBalanceWalletResponse(
        Long id,
        Long userId,
        BigDecimal balance
) {
}
