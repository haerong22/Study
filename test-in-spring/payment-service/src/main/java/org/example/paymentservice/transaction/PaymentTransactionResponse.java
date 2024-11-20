package org.example.paymentservice.transaction;

import java.math.BigDecimal;

public record PaymentTransactionResponse(Long walletId, BigDecimal balance) {
}
