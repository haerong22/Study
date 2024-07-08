package org.example.ledgerservice.ledger.adapter.out.persistence.entity

import org.example.ledgerservice.ledger.domain.PaymentOrder
import org.springframework.stereotype.Component

@Component
class JpaPaymentOrderMapper {

  fun mapToDomainEntity(jpaPaymentOrderEntity: JpaPaymentOrderEntity): PaymentOrder {
    return PaymentOrder(
      id = jpaPaymentOrderEntity.id!!,
      amount = jpaPaymentOrderEntity.amount.toLong(),
      orderId = jpaPaymentOrderEntity.orderId
    )
  }
}