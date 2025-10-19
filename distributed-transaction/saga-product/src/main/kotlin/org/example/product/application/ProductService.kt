package org.example.product.application

import org.example.product.application.dto.ProductBuyCommand
import org.example.product.application.dto.ProductBuyResult
import org.example.product.domain.ProductTransactionHistory
import org.example.product.domain.TransactionType
import org.example.product.infrastructure.ProductRepository
import org.example.product.infrastructure.ProductTransactionHistoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productTransactionHistoryRepository: ProductTransactionHistoryRepository
) {

    @Transactional
    fun buy(cmd: ProductBuyCommand): ProductBuyResult {
        val histories = productTransactionHistoryRepository.findAllByRequestIdAndTransactionType(
            cmd.requestId,
            TransactionType.PURCHASE
        )

        if (histories.isNotEmpty()) {
            println("이미 구매한 이력이 있습니다.")

            val totalPrice = histories.sumOf { it.price }
            return ProductBuyResult(totalPrice)
        }

        var totalPrice: Long = 0

        cmd.productInfos.forEach {
            val product = productRepository.findById(it.productId).orElseThrow()

            product.buy(it.quantity)
            val price = product.calculatePrice(it.quantity)
            totalPrice += price

            val history = ProductTransactionHistory(cmd.requestId, it.productId, it.quantity, price, TransactionType.PURCHASE)
            productTransactionHistoryRepository.save(history)
        }

        return ProductBuyResult(totalPrice)
    }
}