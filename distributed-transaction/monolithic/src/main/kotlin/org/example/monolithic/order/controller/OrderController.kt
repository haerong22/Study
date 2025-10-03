package org.example.monolithic.order.controller

import org.example.monolithic.order.application.OrderService
import org.example.monolithic.order.application.dto.CreateOrderResult
import org.example.monolithic.order.controller.dto.CreateOrderRequest
import org.example.monolithic.order.controller.dto.PlaceOrderRequest
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
    ): CreateOrderResult {
        val result = orderService.createOrder(request.toCommand())
        return CreateOrderResult(result.orderId)
    }

    @PostMapping("/orders/place")
    fun placeOrder(
        @RequestBody request: PlaceOrderRequest
    ) {
        orderService.placeOrder(request.toCommand())
    }
}