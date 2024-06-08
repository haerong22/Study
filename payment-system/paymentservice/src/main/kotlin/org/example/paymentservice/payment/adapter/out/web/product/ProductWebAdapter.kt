package org.example.paymentservice.payment.adapter.out.web.product

import org.example.paymentservice.common.WebAdapter
import org.example.paymentservice.payment.adapter.out.web.product.client.ProductClient
import org.example.paymentservice.payment.application.port.out.LoadProductPort
import org.example.paymentservice.payment.domain.Product
import reactor.core.publisher.Flux

@WebAdapter
class ProductWebAdapter(
    private val productClient: ProductClient,
) : LoadProductPort {

    override fun getProducts(cartId: Long, productIds: List<Long>): Flux<Product> {
        return productClient.getProducts(cartId, productIds)
    }
}