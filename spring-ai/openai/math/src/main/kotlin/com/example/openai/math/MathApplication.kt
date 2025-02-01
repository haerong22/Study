package com.example.openai.math

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MathApplication

fun main(args: Array<String>) {
    runApplication<MathApplication>(*args)
}