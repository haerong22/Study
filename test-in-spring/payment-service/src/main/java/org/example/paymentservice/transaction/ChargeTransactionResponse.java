package org.example.paymentservice.transaction;

import java.math.BigDecimal;

public record ChargeTransactionResponse(Long walletId, BigDecimal balance) {
}
