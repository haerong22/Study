package org.example.sociallogin.social.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    @Bean
    fun googleWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl("https://oauth2.googleapis.com")
            .build()
    }
}