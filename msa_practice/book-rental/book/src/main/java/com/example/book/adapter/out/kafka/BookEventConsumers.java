package com.example.book.adapter.out.kafka;

import com.example.book.application.port.in.MakeAvailableUseCase;
import com.example.book.application.port.in.MakeUnavailableUseCase;
import com.example.book.domain.model.event.ItemRented;
import com.example.book.domain.model.event.ItemReturned;
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
public class BookEventConsumers {

    private final ObjectMapper objectMapper;
    private final MakeAvailableUseCase makeAvailableUsecase;
    private final MakeUnavailableUseCase makeUnavailableUseCase;

    @KafkaListener(topics = "${consumer.topic1.name}", groupId = "${consumer.groupId.name}")
    public void consumeRental(ConsumerRecord<String, String> record) throws JsonProcessingException {
        log.info("rental_rent: {}", record.value());
        ItemRented itemRented = objectMapper.readValue(record.value(), ItemRented.class);
        makeUnavailableUseCase.unavailable(itemRented.getItem().getNo());
    }

    @KafkaListener(topics = "${consumer.topic2.name}", groupId = "${consumer.groupId.name}")
    public void consumeReturn(ConsumerRecord<String, String> record) throws JsonProcessingException {
        log.info("rental_return: {}", record.value());
        ItemReturned itemReturned = objectMapper.readValue(record.value(), ItemReturned.class);
        makeAvailableUsecase.available(itemReturned.getItem().getNo());
    }
}
