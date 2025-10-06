package org.example.product.controller

import org.example.product.application.ProductService
import org.example.product.application.RedisLockService
import org.example.product.controller.dto.ProductReserveRequest
import org.example.product.controller.dto.ProductReserveResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(
    private val productService: ProductService,
    private val redisLockService: RedisLockService,
) {

    @PostMapping("/products/reserve")
    fun reserve(
        @RequestBody request: ProductReserveRequest
    ): ProductReserveResponse {
        val key = "product:${request.requestId}"
        val acquiredLock = redisLockService.tryLock(key, request.requestId)

        if (!acquiredLock) {
            throw RuntimeException("락 획득에 실패하였습니다.")
        }

        try {
            val result = productService.tryReserve(request.toCommand())
            return ProductReserveResponse(result.totalPrice)
        } finally {
            redisLockService.releaseLock(key)
        }
    }
}