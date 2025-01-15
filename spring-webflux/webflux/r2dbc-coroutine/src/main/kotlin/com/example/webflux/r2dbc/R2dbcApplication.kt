package com.example.webflux.r2dbc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class R2dbcApplication

fun main(args: Array<String>) {
    runApplication<R2dbcApplication>(*args)
}