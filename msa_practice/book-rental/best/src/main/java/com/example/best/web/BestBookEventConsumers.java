package com.example.best.web;

import com.example.best.domain.event.ItemRented;
import com.example.best.domain.model.Item;
import com.example.best.domain.service.BestBookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BestBookEventConsumers {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BestBookService bestBookService;

    @KafkaListener(topics = "rental_rent", groupId = "best")
    public void consume(ConsumerRecord<String, String> record) throws JsonProcessingException {
        log.info("rental_rent: {}", record.value());
        ItemRented itemRented = objectMapper.readValue(record.value(), ItemRented.class);
        bestBookService.dealBestBook(Item.create(itemRented.getItem().getNo(), itemRented.getItem().getTitle()));
    }
}