package org.example.walletservice.wallet.adapter.out.persistence.repository

import org.assertj.core.api.Assertions.assertThat
import org.example.walletservice.wallet.adapter.out.persistence.entity.JpaWalletEntity
import org.example.walletservice.wallet.adapter.out.persistence.entity.JpaWalletMapper
import org.example.walletservice.wallet.domain.Item
import org.example.walletservice.wallet.domain.ReferenceType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import java.util.UUID
import java.util.concurrent.Executors

@SpringBootTest
class JpaWalletRepositoryTest (
    @Autowired private val walletRepository: JpaWalletRepository,
    @Autowired private val springDataJpaWalletRepository: SpringDataJpaWalletRepository,
    @Autowired private val springDataJpaWalletTransactionRepository: SpringDataJpaWalletTransactionRepository,
    @Autowired private val jpaWalletMapper: JpaWalletMapper,
) {

    @BeforeEach
    fun clean() {
        springDataJpaWalletTransactionRepository.deleteAllInBatch()
        springDataJpaWalletRepository.deleteAllInBatch()
    }

    @RepeatedTest(value = 5)
    fun `should update the balance of wallet successfully when execute the updated command at the same time`() {
        val jpaWalletEntity1 = JpaWalletEntity(
            userId = 1,
            balance = BigDecimal.ZERO,
            version = 0
        )

        val jpaWalletEntity2 = JpaWalletEntity(
            userId = 2,
            balance = BigDecimal.ZERO,
            version = 0
        )

        springDataJpaWalletRepository.saveAll(listOf(jpaWalletEntity1, jpaWalletEntity2))

        val baseWallet1 = jpaWalletMapper.mapToDomainEntity(jpaWalletEntity1)
        val baseWallet2 = jpaWalletMapper.mapToDomainEntity(jpaWalletEntity2)

        val items1 = listOf(
            Item(
                amount = 1000L,
                orderId = UUID.randomUUID().toString(),
                referenceType = ReferenceType.PAYMENT_ORDER,
                referenceId = 1L
            )
        )

        val items2 = listOf(
            Item(
                amount = 2000L,
                orderId = UUID.randomUUID().toString(),
                referenceType = ReferenceType.PAYMENT_ORDER,
                referenceId = 2L
            )
        )

        val items3 = listOf(
            Item(
                amount = 3000L,
                orderId = UUID.randomUUID().toString(),
                referenceType = ReferenceType.PAYMENT_ORDER,
                referenceId = 3L
            )
        )

        val updatedWallet1 = baseWallet1.calculateBalanceWith(items1)
        val updatedWallet2 = baseWallet1.calculateBalanceWith(items2)
        val updatedWallet3 = baseWallet1.calculateBalanceWith(items3)

        val updatedWallet4 = baseWallet2.calculateBalanceWith(items1)
        val updatedWallet5 = baseWallet2.calculateBalanceWith(items2)
        val updatedWallet6 = baseWallet2.calculateBalanceWith(items3)

        val executorService = Executors.newFixedThreadPool(3)

        val future1 = executorService.submit { walletRepository.save(listOf(updatedWallet1, updatedWallet4)) }
        val future2 = executorService.submit { walletRepository.save(listOf(updatedWallet2, updatedWallet5)) }
        val future3 = executorService.submit { walletRepository.save(listOf(updatedWallet3, updatedWallet6)) }

        future1.get()
        future2.get()
        future3.get()

        val retrievedWallet1 = springDataJpaWalletRepository.findById(baseWallet1.id).get()
        val retrievedWallet2 = springDataJpaWalletRepository.findById(baseWallet2.id).get()

        assertThat(retrievedWallet1.version).isEqualTo(3)
        assertThat(retrievedWallet2.version).isEqualTo(3)

        assertThat(retrievedWallet1.balance.toInt()).isEqualTo(6000)
        assertThat(retrievedWallet2.balance.toInt()).isEqualTo(6000)
    }
}