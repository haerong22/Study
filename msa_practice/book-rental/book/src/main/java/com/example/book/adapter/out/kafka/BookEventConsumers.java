package com.example.book.adapter.out.kafka;

import com.example.book.application.port.in.MakeAvailableUseCase;
import com.example.book.application.port.in.MakeUnavailableUseCase;
import com.example.book.domain.model.event.EventResult;
import com.example.book.domain.model.event.EventType;
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
    private final BookEventProducers bookEventProducers;

    @KafkaListener(topics = "${consumer.topic1.name}", groupId = "${consumer.groupId.name}")
    public void consumeRental(ConsumerRecord<String, String> record) throws JsonProcessingException {
        log.info("rental_rent: {}", record.value());
        ItemRented itemRented = objectMapper.readValue(record.value(), ItemRented.class);

        EventResult eventResult = new EventResult();
        eventResult.setEventType(EventType.RENT);
        eventResult.setIdName(itemRented.getIdName());
        eventResult.setItem(itemRented.getItem());
        eventResult.setPoint(itemRented.getPoint());

        try {
            makeUnavailableUseCase.unavailable(itemRented.getItem().getNo());
            eventResult.setSuccess(true);
        } catch (Exception e) {
            log.info("도서 대여 실패");
            eventResult.setSuccess(false);
        }

        bookEventProducers.produce(eventResult);
    }

    @KafkaListener(topics = "${consumer.topic2.name}", groupId = "${consumer.groupId.name}")
    public void consumeReturn(ConsumerRecord<String, String> record) throws JsonProcessingException {
        log.info("rental_return: {}", record.value());
        ItemReturned itemReturned = objectMapper.readValue(record.value(), ItemReturned.class);

        EventResult eventResult = new EventResult();
        eventResult.setEventType(EventType.RETURN);
        eventResult.setIdName(itemReturned.getIdName());
        eventResult.setItem(itemReturned.getItem());
        eventResult.setPoint(itemReturned.getPoint());

        try {
            makeAvailableUsecase.available(itemReturned.getItem().getNo());
            eventResult.setSuccess(true);
        } catch (Exception e) {
            log.info("도서 반납 실패");
            eventResult.setSuccess(false);
        }

        bookEventProducers.produce(eventResult);
    }
}
