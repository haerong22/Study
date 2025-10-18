package org.example.order

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.retry.annotation.EnableRetry

@EnableRetry
@SpringBootApplication
class OrderApplication

fun main(args: Array<String>) {
    runApplication<OrderApplication>(*args)
}
