package org.example.trxbatch.config;

import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${email-server.url}")
    private String emailServerAddress;

    @Value("${email-server.api-key}")
    private String emailServerApiKey;

    @Bean
    ClientHttpRequestFactory defaultRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
    }

    @Bean
    RestClient emailServerRestClient(RestClient.Builder restClientBuilder, ClientHttpRequestFactory defaultRequestFactory) {
        return restClientBuilder.requestFactory(defaultRequestFactory)
                .baseUrl(emailServerAddress)
                .defaultHeader("X-API-KEY", emailServerApiKey)
                .build();
    }
}