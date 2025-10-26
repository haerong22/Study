package org.example.order.infrastructure

import org.example.order.domain.CompensationRegistry
import org.springframework.data.jpa.repository.JpaRepository

interface CompensationRegistryRepository : JpaRepository<CompensationRegistry, Long>