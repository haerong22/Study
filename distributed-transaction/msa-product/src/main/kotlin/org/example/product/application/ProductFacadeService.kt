package org.example.product.application

import org.example.product.application.dto.ProductReserveCommand
import org.example.product.application.dto.ProductReserveResult
import org.springframework.orm.ObjectOptimisticLockingFailureException
import org.springframework.stereotype.Component

@Component
class ProductFacadeService(
    private val productService: ProductService,
) {

    fun tryReserve(cmd: ProductReserveCommand): ProductReserveResult {
        var tryCount = 0

        while (tryCount < 3) {
            try {
                return productService.tryReserve(cmd)
            } catch (e: ObjectOptimisticLockingFailureException) {
                tryCount++
            }
        }

        throw RuntimeException("예약에 실패했습니다.")
    }
}