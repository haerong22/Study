package org.example.kotlintestframework

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinTestFrameworkApplication

fun main(args: Array<String>) {
    runApplication<KotlinTestFrameworkApplication>(*args)
}
