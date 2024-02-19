package org.example.delivery.api.config.health;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.delivery.api.common.rabbitmq.Producer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/open-api")
@RequiredArgsConstructor
public class HealthOpenApiController {

    private final Producer producer;

    @GetMapping("/health")
    public void health() {
        log.info("health call");
//        producer.producer("delivery.exchange", "delivery.key", "hello");
    }
}
