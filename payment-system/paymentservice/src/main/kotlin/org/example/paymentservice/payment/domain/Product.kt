package org.example.paymentservice.payment.domain

data class Product(
    val id: Long,
    val amount: Long,
    val quantity: Int,
    val name: String,
    val sellerId: Long,
)