package org.example.delivery.gateway.filter

import org.example.delivery.common.error.TokenErrorCode
import org.example.delivery.common.exception.ApiException
import org.example.delivery.gateway.account.model.TokenDto
import org.example.delivery.gateway.account.model.TokenValidationRequest
import org.example.delivery.gateway.account.model.TokenValidationResponse
import org.example.delivery.gateway.common.Log
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

@Component
class ServiceApiPrivateFilter : AbstractGatewayFilterFactory<ServiceApiPrivateFilter.Config>(Config::class.java) {

    companion object : Log

    class Config

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val uri = exchange.request.uri

            log.info("service api private filter route uri: {}", uri)

            val headers = exchange.request.headers["Authorization-token"] ?: listOf()

            val token = if (headers.isEmpty()) {
                throw ApiException(TokenErrorCode.AUTHORIZATION_TOKEN_NOT_FOUND)
            } else {
                headers[0]
            }

            log.info("authorization token: {}", token)

            val accountApiUrl = UriComponentsBuilder
                .fromUriString("http://localhost")
                .port(8082)
                .path("/internal-api/token/validation")
                .build()
                .encode()
                .toUriString()

            val webClient = WebClient.builder().baseUrl(accountApiUrl).build()

            val request = TokenValidationRequest(
                tokenDto = TokenDto(
                    token = token,
                )
            )

            webClient
                .post()
                .body(Mono.just(request), object : ParameterizedTypeReference<TokenValidationRequest>() {})
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                    { status: HttpStatusCode? ->
                        status?.isError ?: true
                    },
                    { response: ClientResponse ->
                        response.bodyToMono(object : ParameterizedTypeReference<Any>() {})
                            .flatMap { error ->
                                log.error("", error)

                                Mono.error(ApiException(TokenErrorCode.TOKEN_EXCEPTION))
                            }
                    }
                )
                .bodyToMono(object : ParameterizedTypeReference<TokenValidationResponse>() {})
                .flatMap { response ->
                    log.info("response: {}", response)

                    val userid = response.userId?.toString()

                    val proxyRequest = exchange.request.mutate()
                        .header("x-user-id", userid)
                        .build()

                    val requestBuild = exchange.mutate().request(proxyRequest).build()

                    val mono = chain.filter(requestBuild)
                    mono
                }
                .onErrorMap {e ->
                    log.error("", e)
                    e
                }
        }
    }
}