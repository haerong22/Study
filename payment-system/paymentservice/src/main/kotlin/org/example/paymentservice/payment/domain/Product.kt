package org.example.paymentservice.payment.domain

import java.math.BigDecimal

data class Product(
    val id: Long,
    val amount: BigDecimal,
    val quantity: Int,
    val name: String,
    val sellerId: Long,
)