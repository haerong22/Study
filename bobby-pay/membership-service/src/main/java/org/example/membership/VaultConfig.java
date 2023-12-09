package org.example.membership;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultToken;

@Configuration
public class VaultConfig {

    @Value("${spring.cloud.vault.token}")
    private String token;

    @Value("${spring.cloud.vault.scheme}")
    private String scheme;

    @Value("${spring.cloud.vault.host}")
    private String host;

    @Value("${spring.cloud.vault.port}")
    private int port;

    @Bean
    public VaultTemplate vaultTemplate() {
        VaultEndpoint vaultEndpoint = VaultEndpoint.create(host, port);
        vaultEndpoint.setScheme(scheme);

        return new VaultTemplate(vaultEndpoint, () -> VaultToken.of(token));
    }
}
