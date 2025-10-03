package org.example.monolithic.order.application

import org.example.monolithic.order.application.dto.CreateOrderCommand
import org.example.monolithic.order.application.dto.CreateOrderResult
import org.example.monolithic.order.application.dto.PlaceOrderCommand
import org.example.monolithic.order.domain.Order
import org.example.monolithic.order.domain.OrderItem
import org.example.monolithic.order.infrastructure.OrderItemRepository
import org.example.monolithic.order.infrastructure.OrderRepository
import org.example.monolithic.point.application.PointService
import org.example.monolithic.product.application.ProductService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val pointService: PointService,
    private val productService: ProductService,
) {

    @Transactional
    fun createOrder(cmd: CreateOrderCommand): CreateOrderResult {
        val order = orderRepository.save(Order())
        val orderItems = cmd.orderItems.map { OrderItem(order.id!!, it.productId, it.quantity) }
        orderItemRepository.saveAll(orderItems)
        return CreateOrderResult(order.id!!)
    }

    @Transactional
    fun placeOrder(cmd: PlaceOrderCommand) {
        val order = orderRepository.findById(cmd.orderId).orElseThrow { throw RuntimeException("주문정보가 존재하지 않습니다.") }

        if (order.isCompleted()) return

        var totalPrice = 0L
        val orderItems = orderItemRepository.findAllByOrderId(order.id!!)

        for (item in orderItems) {
            val price = productService.buy(item.productId, item.quantity)
            totalPrice += price
        }

        pointService.use(1L, totalPrice)

        order.complete()
    }

}