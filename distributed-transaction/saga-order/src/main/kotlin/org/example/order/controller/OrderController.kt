package org.example.order.controller

import org.example.order.application.OrderService
import org.example.order.controller.dto.CreateOrderRequest
import org.example.order.controller.dto.CreateOrderResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(
    private val orderService: OrderService,
) {

    @PostMapping("/orders")
    fun createOrder(
        @RequestBody request: CreateOrderRequest
    ): CreateOrderResponse {
        val result = orderService.createOrder(request.toCommand())
        return CreateOrderResponse(result.orderId)
    }
}