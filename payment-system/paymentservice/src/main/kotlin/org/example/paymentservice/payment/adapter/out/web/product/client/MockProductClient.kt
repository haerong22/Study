package org.example.paymentservice.payment.adapter.out.web.product.client

import org.example.paymentservice.payment.domain.Product
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.math.BigDecimal

@Component
class MockProductClient : ProductClient {

    override fun getProducts(cartId: Long, productIds: List<Long>): Flux<Product> {
        return Flux.fromIterable(
            productIds.map {
                Product(
                    id = it,
                    amount = BigDecimal(it * 10000),
                    quantity = 2,
                    name = "test_product_$it",
                    sellerId = 1,
                )
            }
        )
    }
}