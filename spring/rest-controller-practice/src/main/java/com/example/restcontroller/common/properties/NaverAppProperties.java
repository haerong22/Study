package com.example.restcontroller.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("naver-api")
public class NaverAppProperties {

    private String clientId;
    private String clientSecret;
}
