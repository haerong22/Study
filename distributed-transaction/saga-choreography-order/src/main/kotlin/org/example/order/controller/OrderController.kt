package org.example.order.controller

import org.example.order.application.OrderService
import org.example.order.application.RedisLockService
import org.example.order.controller.dto.PlaceOrderRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(
    private val orderService: OrderService,
    private val redisLockService: RedisLockService,
) {

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

}