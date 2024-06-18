package org.example.paymentservice.payment.test

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.http.HttpHeaders
import org.springframework.http.client.reactive.ClientHttpConnector
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.util.Base64

@TestConfiguration
class PSPTestWebClientConfiguration (
    @Value("\${psp.toss.url}") private val baseUrl: String,
    @Value("\${psp.toss.secret-key}") private val secretKey: String,
) {

    fun createTestTossWebClient(vararg customHeaderKeyValue: Pair<String, String>): WebClient {
        val encodedSecretKey = Base64.getEncoder().encodeToString((secretKey + ":").toByteArray())

        return WebClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic $encodedSecretKey")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
            .defaultHeaders { httpHeaders ->
                customHeaderKeyValue.forEach { httpHeaders[it.first] = it.second }
            }
            .clientConnector(reactorClientHttpConnector())
            .build()
    }

    private fun reactorClientHttpConnector(): ClientHttpConnector {
        return ReactorClientHttpConnector(HttpClient.create(ConnectionProvider.builder("test-toss-payment").build()))
    }
}