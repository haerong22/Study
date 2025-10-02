package org.example.monolithic.order.controller

import org.example.monolithic.order.application.OrderService
import org.example.monolithic.order.controller.dto.PlaceOrderRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(
    private val orderService: OrderService,
) {

    @PostMapping("/orders/place")
    fun placeOrder(
        @RequestBody request: PlaceOrderRequest
    ) {
        return orderService.placeOrder(request.toCommand())
    }
}