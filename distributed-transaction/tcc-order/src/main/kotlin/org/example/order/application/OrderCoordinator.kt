package org.example.order.application

import org.example.order.application.dto.PlaceOrderCommand
import org.example.order.infrastructure.point.PointApiClient
import org.example.order.infrastructure.point.dto.PointReserveApiRequest
import org.example.order.infrastructure.point.dto.PointReserveCancelApiRequest
import org.example.order.infrastructure.point.dto.PointReserveConfirmApiRequest
import org.example.order.infrastructure.product.ProductApiClient
import org.example.order.infrastructure.product.dto.ProductReserveApiRequest
import org.example.order.infrastructure.product.dto.ProductReserveCancelApiRequest
import org.example.order.infrastructure.product.dto.ProductReserveConfirmApiRequest
import org.springframework.stereotype.Component

@Component
class OrderCoordinator(
    private val orderService: OrderService,
    private val productApiClient: ProductApiClient,
    private val pointApiClient: PointApiClient,
) {

    fun placeOrder(cmd: PlaceOrderCommand) {
        reserve(cmd.orderId)
        confirm(cmd.orderId)
    }

    private fun reserve(orderId: Long) {
        val requestId = orderId.toString()

        orderService.reserve(orderId)

        try {
            val order = orderService.getOrder(orderId)

            val productReserveApiRequest = ProductReserveApiRequest(
                requestId,
                order.orderItems.map { ProductReserveApiRequest.ReserveItem(it.productId, it.quantity) },
            )

            val productReserveApiResponse = productApiClient.reserveProduct(productReserveApiRequest)

            val pointReserveApiRequest = PointReserveApiRequest(
                requestId,
                1L,
                productReserveApiResponse.totalPrice,
            )

            pointApiClient.reservePoint(pointReserveApiRequest)
        } catch (e: Exception) {
            orderService.cancel(orderId)

            val productReserveCancelApiRequest = ProductReserveCancelApiRequest(requestId)
            productApiClient.cancelProduct(productReserveCancelApiRequest)

            val pointReserveCancelApiRequest = PointReserveCancelApiRequest(requestId)
            pointApiClient.cancelPoint(pointReserveCancelApiRequest)
        }
    }

    fun confirm(orderId: Long) {
        val requestId = orderId.toString()

        try {
            val productReserveConfirmApiRequest = ProductReserveConfirmApiRequest(requestId)
            productApiClient.confirmProduct(productReserveConfirmApiRequest)

            val pointReserveConfirmApiRequest = PointReserveConfirmApiRequest(requestId)
            pointApiClient.confirmPoint(pointReserveConfirmApiRequest)

            orderService.confirm(orderId)
        } catch (e: Exception) {
            orderService.pending(orderId)
            throw e
        }
    }
}