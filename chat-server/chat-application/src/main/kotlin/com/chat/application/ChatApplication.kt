package com.chat.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(
    scanBasePackages = [
        "com.chat.application",
        "com.chat.domain",
        "com.chat.persistence",
        "com.chat.api",
        "com.chat.websocket"
    ]
)
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = ["com.chat.persistence.repository"])
@EntityScan(basePackages = ["com.chat.domain.model"])
class ChatApplication

fun main(args: Array<String>) {
    runApplication<ChatApplication>(*args)
}