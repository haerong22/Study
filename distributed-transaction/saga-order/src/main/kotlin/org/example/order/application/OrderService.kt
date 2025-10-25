package org.example.order.application

import org.example.order.application.dto.CreateOrderCommand
import org.example.order.application.dto.CreateOrderResult
import org.example.order.application.dto.OrderDto
import org.example.order.domain.Order
import org.example.order.domain.OrderItem
import org.example.order.infrastructure.OrderItemRepository
import org.example.order.infrastructure.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
) {

    @Transactional
    fun createOrder(cmd: CreateOrderCommand): CreateOrderResult {
        val order = orderRepository.save(Order())

        val orderItems = cmd.items.map { OrderItem(order.id!!, it.productid, it.quantity) }

        orderItemRepository.saveAll(orderItems)

        return CreateOrderResult(order.id!!)
    }

    @Transactional(readOnly = true)
    fun getOrder(orderId: Long): OrderDto {
        val orderItems = orderItemRepository.findAllByOrderId(orderId)

        return OrderDto(orderItems.map { OrderDto.OrderItem(it.orderId, it.quantity) })
    }

    @Transactional
    fun request(orderId: Long) {
        val order = orderRepository.findById(orderId).orElseThrow()

        order.request()
    }

    @Transactional
    fun complete(orderId: Long) {
        val order = orderRepository.findById(orderId).orElseThrow()

        order.complete()
    }

    @Transactional
    fun fail(orderId: Long) {
        val order = orderRepository.findById(orderId).orElseThrow()

        order.fail()
    }
}