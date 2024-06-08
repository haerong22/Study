package org.example.paymentservice.payment.adapter.out.web.product.client

import org.example.paymentservice.payment.domain.Product
import reactor.core.publisher.Flux

interface ProductClient {

    fun getProducts(cartId: Long, productIds: List<Long>) : Flux<Product>
}