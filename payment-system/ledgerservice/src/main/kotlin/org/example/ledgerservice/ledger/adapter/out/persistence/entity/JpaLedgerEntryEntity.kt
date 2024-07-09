package org.example.ledgerservice.ledger.adapter.out.persistence.entity

import jakarta.persistence.*
import org.example.ledgerservice.ledger.domain.LedgerEntryType
import java.math.BigDecimal

@Entity
@Table(name = "ledger_entries")
class JpaLedgerEntryEntity (
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  val amount: BigDecimal,

  @Column(name = "account_id")
  val accountId: Long,

  @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
  val transaction: JpaLedgerTransactionEntity,

  @Enumerated(EnumType.STRING)
  val type: LedgerEntryType
)