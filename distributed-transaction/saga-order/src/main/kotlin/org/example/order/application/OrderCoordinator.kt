package org.example.order.application

import org.example.order.application.dto.PlaceOrderCommand
import org.example.order.domain.CompensationRegistry
import org.example.order.infrastructure.CompensationRegistryRepository
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
    private val compensationRegistryRepository: CompensationRegistryRepository,
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
            rollback(cmd.orderId)
            throw e
        }
    }

    private fun rollback(orderId: Long) {
        try {
            val productBuyCancelApiRequest = ProductBuyCancelApiRequest(orderId.toString())
            val pointBuyCancelApiResponse = productApiClient.cancel(productBuyCancelApiRequest)

            if (pointBuyCancelApiResponse.totalPrice > 0) {
                val pointUseCancelApiRequest = PointUseCancelApiRequest(orderId.toString())
                pointApiClient.cancel(pointUseCancelApiRequest)
            }

            orderService.fail(orderId)
        } catch (e: Exception) {
            val compensationRegistry = CompensationRegistry(orderId)
            compensationRegistryRepository.save(compensationRegistry)
            throw e
        }
    }
}