package com.example.openai.tts

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TTSApplication

fun main(args: Array<String>) {
    runApplication<TTSApplication>(*args)
}