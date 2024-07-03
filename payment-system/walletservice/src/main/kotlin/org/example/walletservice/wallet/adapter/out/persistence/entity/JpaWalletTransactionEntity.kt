package org.example.walletservice.wallet.adapter.out.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.example.walletservice.wallet.domain.TransactionType
import java.math.BigDecimal

@Entity
@Table(name = "wallet_transactions")
class JpaWalletTransactionEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "wallet_id")
    val walletId: Long,

    @Column
    val amount: BigDecimal,

    @Enumerated(EnumType.STRING)
    val type: TransactionType,

    @Column(name = "order_id")
    val orderId: String,

    @Column(name = "reference_type")
    val referenceType: String,

    @Column(name = "reference_id")
    val referenceId: Long,

    @Column(name = "idempotency_key")
    val idempotencyKey: String,

)