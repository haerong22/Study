package org.example.order.domain

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "compensation_registries")
class CompensationRegistry(
    val orderId: Long,
    @Enumerated(EnumType.STRING)
    var status: CompensationRegistryStatus = CompensationRegistryStatus.PENDING
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}

enum class CompensationRegistryStatus {
    PENDING, COMPLETE
}