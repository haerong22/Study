package com.example.catalogservice.messagequeue;

import com.example.catalogservice.entity.CatalogEntity;
import com.example.catalogservice.repository.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final CatalogRepository catalogRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "example-catalog-topic")
    public void updateQty(String kafkaMessage) {
        log.info("Kafka Message: -> " + kafkaMessage);

        HashMap<Object, Object> map = new HashMap<>();

        try {
            map = objectMapper.readValue(kafkaMessage, new TypeReference<HashMap<Object, Object>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String productId = (String) map.get("productId");

        CatalogEntity catalogEntity = catalogRepository.findByProductId(productId)
                .orElseThrow(RuntimeException::new);

        catalogEntity.setStock(catalogEntity.getStock() - (Integer) map.get("qty"));

        catalogRepository.save(catalogEntity);
    }
}
