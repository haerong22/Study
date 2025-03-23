package org.example.goopang.service

import org.apache.coyote.BadRequestException
import org.example.goopang.entity.product.Clothing
import org.example.goopang.entity.product.Electronics
import org.example.goopang.entity.product.Product
import org.example.goopang.entity.product.ProductType
import org.example.goopang.input.AddProductInput
import org.example.goopang.repository.Database
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import reactor.util.concurrent.Queues
import java.util.UUID

@Service
class ProductService {

    private val productSink = Sinks.many().multicast().onBackpressureBuffer<Product>(Queues.SMALL_BUFFER_SIZE, false)

    fun getProduct(productId: String): Product {
        return Database.products.firstOrNull { it.id == productId }
            ?: throw BadRequestException("Product not found")
    }

    fun getProducts(): List<Product> {
        return Database.products
    }

    fun addProduct(addProductInput: AddProductInput): Product {
        val product = when (addProductInput.productType) {
            ProductType.ELECTRONICS -> {
                if (addProductInput.warrantyPeriod == null) {
                    throw BadRequestException("Warranty period is required")
                }
                Electronics(
                    id = UUID.randomUUID().toString().substring(0, 5),
                    name = addProductInput.name,
                    price = addProductInput.price,
                    warrantyPeriod = addProductInput.warrantyPeriod,
                )
            }

            ProductType.CLOTHING -> {
                if (addProductInput.size == null) {
                    throw BadRequestException("Size is required")
                }
                Clothing(
                    id = UUID.randomUUID().toString().substring(0, 5),
                    name = addProductInput.name,
                    price = addProductInput.price,
                    size = addProductInput.size,
                )
            }
        }
        Database.products.add(product)

        return product
    }

    fun messageFlux(productName: String?): Flux<Product> {
        return productSink.asFlux()
            .let { product ->
                if (productName != null) {
                    product.filter { it.name.contains(productName) }
                } else {
                    product
                }
            }
    }
}