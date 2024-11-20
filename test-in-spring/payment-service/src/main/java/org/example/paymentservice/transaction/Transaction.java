package org.example.paymentservice.transaction;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transaction {

    @Id
    private Long id;

    private Long userId;

    private Long walletId;

    private String orderId;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private BigDecimal amount;

    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Transaction createChargeTransaction(
            Long userId, Long walletId, String orderId, BigDecimal amount
    ) {
        Transaction transaction = new Transaction();
        transaction.userId = userId;
        transaction.walletId = walletId;
        transaction.orderId = orderId;
        transaction.transactionType = TransactionType.CHARGE;
        transaction.amount = amount;
        transaction.description = "충전";
        return transaction;
    }
}
