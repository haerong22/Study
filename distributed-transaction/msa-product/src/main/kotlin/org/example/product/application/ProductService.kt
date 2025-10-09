package org.example.product.application

import org.example.product.application.dto.ProductReserveCancelCommand
import org.example.product.application.dto.ProductReserveCommand
import org.example.product.application.dto.ProductReserveConfirmCommand
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

    @Transactional
    fun confirmReserve(cmd: ProductReserveConfirmCommand) {
        val reservations = productReservationRepository.findAllByRequestId(cmd.requestId)

        if (reservations.isEmpty()) {
            throw RuntimeException("예약된 정보가 없습니다.")
        }

        val alreadyConfirmed = reservations.any { it.isConfirmed() }

        if (alreadyConfirmed) {
            println("이미 확정이 되었습니다.")
            return
        }

        reservations.forEach { reservation ->
            val product = productRepository.findById(reservation.productId).orElseThrow()
            product.confirm(reservation.reservedQuantity)
            reservation.confirm()
        }
    }

    @Transactional
    fun cancelReserve(cmd: ProductReserveCancelCommand) {
        val reservations = productReservationRepository.findAllByRequestId(cmd.requestId)

        if (reservations.isEmpty()) {
            throw RuntimeException("예약된 정보가 없습니다.")
        }

        val alreadyCancelled = reservations.any { it.isCancelled() }

        if (alreadyCancelled) {
            println("이미 취소된 요청입니다.")
            return
        }

        reservations.forEach { reservation ->
            val product = productRepository.findById(reservation.productId).orElseThrow()
            product.cancel(reservation.reservedQuantity)
            reservation.cancel()
        }
    }
}