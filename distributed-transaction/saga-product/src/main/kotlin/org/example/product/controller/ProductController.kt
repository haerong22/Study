package org.example.product.controller

import org.example.product.application.ProductService
import org.example.product.application.RedisLockService
import org.example.product.controller.dto.ProductBuyRequest
import org.example.product.controller.dto.ProductBuyResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(
    private val productService: ProductService,
    private val redisLockService: RedisLockService,
) {

    @PostMapping("/products/buy")
    fun buy(
        @RequestBody request: ProductBuyRequest,
    ): ProductBuyResponse {
        val key = "product:orchestration:${request.requestId}"

        val acquiredLock = redisLockService.tryLock(key, request.requestId)

        if (!acquiredLock) {
            throw RuntimeException("락 획득에 실패하였습니다.")
        }

        try {
            val result = productService.buy(request.toCommand())
            return ProductBuyResponse(result.totalPrice)
        } finally {
            redisLockService.releaseLock(key)
        }
    }
}