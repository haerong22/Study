package org.example.sociallogin.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController(
    @Value("\${spring.application.name}")
    private val applicationName: String,
) {

    @GetMapping("/health")
    fun ping(): String {
        return "$applicationName is available!!"
    }
}