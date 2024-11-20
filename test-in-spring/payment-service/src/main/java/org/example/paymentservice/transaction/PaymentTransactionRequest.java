package org.example.paymentservice.transaction;

import java.math.BigDecimal;

public record PaymentTransactionRequest(Long walletId, String courseId, BigDecimal amount) {
}
