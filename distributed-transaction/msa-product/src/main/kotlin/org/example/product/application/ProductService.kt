package org.example.product.application

import org.example.product.application.dto.ProductReserveCommand
import org.example.product.application.dto.ProductReserveResult
import org.example.product.domain.ProductReservation
import org.example.product.infrastructure.ProductRepository
import org.example.product.infrastructure.ProductReservationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productReservationRepository: ProductReservationRepository,
) {

    @Transactional
    fun tryReserve(cmd: ProductReserveCommand): ProductReserveResult {
        val reserved = productReservationRepository.findAllByRequestId(cmd.requestId)

        if (reserved.isNotEmpty()) {
            val totalPrice = reserved.sumOf { it.reservationPrice }

            return ProductReserveResult(totalPrice)
        }

        val totalPrice = cmd.items.sumOf { item ->
            val product = productRepository.findById(item.productId).orElseThrow()
            val price = product.reserve(item.reserveQuantity)

            productRepository.save(product)
            productReservationRepository.save(
                ProductReservation(
                    cmd.requestId,
                    item.productId,
                    item.reserveQuantity,
                    price
                )
            )
            price
        }

        return ProductReserveResult(totalPrice)
    }
}