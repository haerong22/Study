package org.example.paymentservice.payment.adapter.out.web.toss.config

import io.netty.handler.timeout.ReadTimeoutHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.client.reactive.ClientHttpConnector
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.util.Base64
import java.util.concurrent.TimeUnit

@Configuration
class TossWebClientConfiguration(
    @Value("\${psp.toss.url}") private val baseUrl: String,
    @Value("\${psp.toss.secret-key}") private val secretKey: String,
) {

    @Bean
    fun tossPaymentWebClient(): WebClient {
        val encodedSecretKey = Base64.getEncoder().encodeToString((secretKey + ":").toByteArray())

        return WebClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic $encodedSecretKey")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
            .clientConnector(reactorClientHttpConnector())
            .codecs { it.defaultCodecs() }
            .build()
    }

    private fun reactorClientHttpConnector(): ClientHttpConnector {
        val provider = ConnectionProvider.builder("toss-payment").build()

        val clientBase = HttpClient.create(provider)
            .doOnConnected {
                it.addHandlerLast(ReadTimeoutHandler(30, TimeUnit.SECONDS))
            }

        return ReactorClientHttpConnector(clientBase)
    }
}