package org.example.sociallogin.gateway.db.config

import org.h2.tools.Server
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ContextClosedEvent
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener

@Configuration
class H2ConsoleConfig(
    @Value("\${h2.console.port}")
    private val port: Int,
) {
    private val log = LoggerFactory.getLogger(H2ConsoleConfig::class.java)

    private lateinit var server: Server

    @EventListener(ContextRefreshedEvent::class)
    fun start() {
        log.info("started h2-console port: $port")
        this.server = Server.createWebServer("-webPort", port.toString()).start()
    }

    @EventListener(ContextClosedEvent::class)
    fun stop() {
        log.info("stopped h2-console port: $port")
        this.server.stop()
    }

}