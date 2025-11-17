package org.example.order.application

import org.example.order.application.dto.CreateOrderCommand
import org.example.order.application.dto.CreateOrderResult
import org.example.order.application.dto.PlaceOrderCommand
import org.example.order.domain.Order
import org.example.order.domain.OrderItem
import org.example.order.domain.OrderStatus
import org.example.order.infrastructure.OrderItemRepository
import org.example.order.infrastructure.OrderRepository
import org.example.order.infrastructure.kafka.OrderPlaceProducer
import org.example.order.infrastructure.kafka.dto.OrderPlacedEvent
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronization
import org.springframework.transaction.support.TransactionSynchronizationManager

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val orderPlaceProducer: OrderPlaceProducer,
) {

    @Transactional
    fun createOrder(cmd: CreateOrderCommand): CreateOrderResult {
        val order = orderRepository.save(Order())

        val orderItems = cmd.items.map { OrderItem(order.id!!, it.productid, it.quantity) }

        orderItemRepository.saveAll(orderItems)

        return CreateOrderResult(order.id!!)
    }

    @Transactional
    fun placeOrder(cmd: PlaceOrderCommand) {
        val order = orderRepository.findById(cmd.orderId).orElseThrow()

        val orderItems = orderItemRepository.findAllByOrderId(order.id!!)

        order.request()
        orderRepository.save(order)

        TransactionSynchronizationManager.registerSynchronization(
            object : TransactionSynchronization {
                override fun afterCommit() {
                    val event = OrderPlacedEvent(
                        cmd.orderId,
                        orderItems.map { OrderPlacedEvent.ProductInfo(it.productId, it.quantity) },
                    )
                    orderPlaceProducer.send(event)
                }
            }
        )
    }

    @Transactional
    fun fail(orderId: Long) {
        val order = orderRepository.findById(orderId).orElseThrow()

        order.fail()
    }

    @Transactional
    fun complete(orderId: Long) {
        val order = orderRepository.findById(orderId).orElseThrow()

        order.complete()
    }

    fun getStatus(orderId: Long): OrderStatus {
        val order = orderRepository.findById(orderId).orElseThrow()

        return order.status
    }
}