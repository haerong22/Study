package org.example.ledgerservice.ledger.adapter.out.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "ledger_transactions")
class JpaLedgerTransactionEntity (

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  val description: String,

  @Column(name = "reference_id")
  val referenceId: Long,

  @Column(name = "reference_type")
  val referenceType: String,

  @Column(name = "order_id")
  val orderId: String,

  @Column(name = "idempotency_key")
  val idempotencyKey: String
)