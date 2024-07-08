package org.example.ledgerservice.ledger.adapter.out.persistence.entity

import org.example.ledgerservice.ledger.domain.Account
import org.springframework.stereotype.Component

@Component
class JpaAccountMapper {

  fun mapToDomainEntity(jpaAccountEntity: JpaAccountEntity): Account {
    return Account(
      id = jpaAccountEntity.id!!,
      name = jpaAccountEntity.name
    )
  }
}