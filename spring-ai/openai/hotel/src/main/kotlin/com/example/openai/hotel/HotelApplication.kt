package com.example.openai.hotel

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HotelApplication

fun main(args: Array<String>) {
    runApplication<HotelApplication>(*args)
}