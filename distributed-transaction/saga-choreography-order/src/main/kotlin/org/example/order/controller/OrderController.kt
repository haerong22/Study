package org.example.order.controller

import org.example.order.application.OrderService
import org.example.order.application.RedisLockService
import org.example.order.controller.dto.CreateOrderRequest
import org.example.order.controller.dto.CreateOrderResponse
import org.example.order.controller.dto.PlaceOrderRequest
import org.example.order.domain.OrderStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(
    private val orderService: OrderService,
    private val redisLockService: RedisLockService,
) {

    @PostMapping("/orders")
    fun createOrder(
        @RequestBody request: CreateOrderRequest
    ): CreateOrderResponse {
        val result = orderService.createOrder(request.toCommand())
        return CreateOrderResponse(result.orderId)
    }

    @PostMapping("/orders/place")
    fun placeOrder(
        @RequestBody request: PlaceOrderRequest,
    ) {
        val key = "order:choreography:${request.orderId}"

        val acquiredLock = redisLockService.tryLock(key, request.orderId.toString())

        if (!acquiredLock) {
            throw RuntimeException("락 획득에 실패하였습니다.")
        }

        try {
            orderService.placeOrder(request.toCommand())
        } finally {
            redisLockService.releaseLock(key)
        }
    }

    @GetMapping("/orders/{orderId}/status")
    fun getStatus(
        @PathVariable orderId: Long,
    ): OrderStatus {
        return orderService.getStatus(orderId)
    }

}