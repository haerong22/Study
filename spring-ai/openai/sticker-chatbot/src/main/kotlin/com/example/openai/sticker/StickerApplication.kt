package com.example.openai.sticker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StickerApplication

fun main(args: Array<String>) {
    runApplication<StickerApplication>(*args)
}