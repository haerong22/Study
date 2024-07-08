package org.example.ledgerservice.ledger.adapter.out.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "payment_orders")
class JpaPaymentOrderEntity (
  @Id
  val id: Long? = null,

  val amount: BigDecimal,

  @Column(name = "order_id")
  val orderId: String
)