package org.example.monolithic.order.application

import org.example.monolithic.order.application.dto.PlaceOrderCommand
import org.example.monolithic.order.domain.Order
import org.example.monolithic.order.domain.OrderItem
import org.example.monolithic.order.infrastructure.OrderItemRepository
import org.example.monolithic.order.infrastructure.OrderRepository
import org.example.monolithic.point.application.PointService
import org.example.monolithic.product.application.ProductService
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val pointService: PointService,
    private val productService: ProductService,
) {

    fun placeOrder(cmd: PlaceOrderCommand) {
        val order = orderRepository.save(Order())
        var totalPrice = 0L

        for (item in cmd.orderItems) {
            val orderItem = OrderItem(order.id!!, item.productId, item.quantity)
            orderItemRepository.save(orderItem)

            val price = productService.buy(item.productId, item.quantity)
            totalPrice += price
        }

        pointService.use(1L, totalPrice)
    }

}