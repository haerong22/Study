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
        val orderItems = cmd.orderItems.map { OrderItem(order.id!!, it.productId, it.quantity) }

        orderItemRepository.saveAll(orderItems)

        return CreateOrderResult(order.id!!)
    }

    @Transactional
    fun reserve(orderId: Long) {
        val order = orderRepository.findById(orderId).orElseThrow()
        order.reserve()
    }

    @Transactional(readOnly = true)
    fun getOrder(orderId: Long): OrderDto {
        val order = orderRepository.findById(orderId).orElseThrow()
        val orderItems = orderItemRepository.findAllByOrderId(orderId)

        return OrderDto(orderItems.map { OrderDto.OrderItem(it.productId, it.quantity) })
    }

    @Transactional
    fun cancel(orderId: Long) {
        val order = orderRepository.findById(orderId).orElseThrow()

        order.cancel()
    }

    @Transactional
    fun confirm(orderId: Long) {
        val order = orderRepository.findById(orderId).orElseThrow()

        order.confirm()
    }

    @Transactional
    fun pending(orderId: Long) {
        val order = orderRepository.findById(orderId).orElseThrow()

        order.pending()
    }
}