package org.example.order.controller

import org.example.order.application.OrderCoordinator
import org.example.order.application.OrderService
import org.example.order.application.RedisLockService
import org.example.order.application.dto.CreateOrderResult
import org.example.order.controller.dto.CreateOrderRequest
import org.example.order.controller.dto.PlaceOrderRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(
    private val orderService: OrderService,
    private val redisLockService: RedisLockService,
    private val orderCoordinator: OrderCoordinator,
) {

    @PostMapping("/orders")
    fun createOrder(
        @RequestBody request: CreateOrderRequest
    ): CreateOrderResult {
        val result = orderService.createOrder(request.toCommand())
        return CreateOrderResult(result.orderId)
    }

    @PostMapping("/orders/place")
    fun placeOrder(
        @RequestBody request: PlaceOrderRequest
    ) {
        val key = "order:${request.orderId}"

        val acquiredLock = redisLockService.tryLock(key, request.orderId.toString())

        if (!acquiredLock) {
            throw RuntimeException("락 획득 실패")
        }

        try {
            orderCoordinator.placeOrder(request.toCommand())
        } finally {
            redisLockService.releaseLock(key)
        }
    }
}