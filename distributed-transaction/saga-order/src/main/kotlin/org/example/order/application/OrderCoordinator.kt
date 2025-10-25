package org.example.order.application

import org.example.order.application.dto.PlaceOrderCommand
import org.example.order.infrastructure.point.PointApiClient
import org.example.order.infrastructure.point.PointUseApiRequest
import org.example.order.infrastructure.point.PointUseCancelApiRequest
import org.example.order.infrastructure.product.ProductApiClient
import org.example.order.infrastructure.product.ProductBuyApiRequest
import org.example.order.infrastructure.product.ProductBuyCancelApiRequest
import org.springframework.stereotype.Component

@Component
class OrderCoordinator(
    private val orderService: OrderService,
    private val productApiClient: ProductApiClient,
    private val pointApiClient: PointApiClient,
) {

    fun placeOrder(cmd: PlaceOrderCommand) {
        orderService.request(cmd.orderId)
        val orderDto = orderService.getOrder(cmd.orderId)

        try {
            val productBuyApiRequest = ProductBuyApiRequest(
                cmd.orderId.toString(),
                orderDto.orderItems.map { ProductBuyApiRequest.ProductInfo(it.productId, it.quantity) }
            )

            val buyApiResponse = productApiClient.buy(productBuyApiRequest)

            val pointUseApiRequest = PointUseApiRequest(
                cmd.orderId.toString(),
                1L,
                buyApiResponse.totalPrice,
            )

            pointApiClient.use(pointUseApiRequest)

            orderService.complete(cmd.orderId)
        } catch (e: Exception) {
            val productBuyCancelApiRequest = ProductBuyCancelApiRequest(cmd.orderId.toString())
            val pointBuyCancelApiResponse = productApiClient.cancel(productBuyCancelApiRequest)

            if (pointBuyCancelApiResponse.totalPrice > 0) {
                val pointUseCancelApiRequest = PointUseCancelApiRequest(cmd.orderId.toString())
                pointApiClient.cancel(pointUseCancelApiRequest)
            }

            orderService.fail(cmd.orderId)
        }
    }
}