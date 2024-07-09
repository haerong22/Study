package org.example.ledgerservice.ledger.adapter.out.persistence.repository

import org.example.ledgerservice.ledger.adapter.out.persistence.entity.JpaLedgerEntryEntity
import org.example.ledgerservice.ledger.adapter.out.persistence.entity.JpaLedgerEntryMapper
import org.example.ledgerservice.ledger.domain.DoubleLedgerEntry
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class JpaLedgerEntryRepository (
  private val springDataJpaLedgerEntryRepository: SpringDataJpaLedgerEntryRepository,
  private val jpaLedgerEntryMapper: JpaLedgerEntryMapper,
) : LedgerEntryRepository {

  override fun save(doubleLedgerEntries: List<DoubleLedgerEntry>) {
    springDataJpaLedgerEntryRepository.saveAll(doubleLedgerEntries.flatMap { jpaLedgerEntryMapper.mapToJpaEntity(it) })
  }
}

interface SpringDataJpaLedgerEntryRepository : JpaRepository<JpaLedgerEntryEntity, Long>