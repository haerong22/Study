package org.example.delivery.api.config.health

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/open-api")
class HealthOpenApiController {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("/health")
    fun health() {
        log.info("health call")
    }
}