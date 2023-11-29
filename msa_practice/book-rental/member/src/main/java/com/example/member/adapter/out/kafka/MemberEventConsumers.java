package com.example.member.adapter.out.kafka;

import com.example.member.application.port.in.SavePointUseCase;
import com.example.member.application.port.in.UsePointUseCase;
import com.example.member.domain.event.*;
import com.example.member.domain.vo.IDName;
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
public class MemberEventConsumers {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SavePointUseCase savePointUsecase;
    private final UsePointUseCase usePointUsecase;
    private final MemberEventProducers memberEventProducers;

    @KafkaListener(topics = "${consumer.topic1.name}", groupId = "${consumer.groupId.name}")
    public void consumeRent(ConsumerRecord<String, String> record) throws JsonProcessingException {
        log.info("rental_rent: {}", record.value());
        ItemRented itemRented = objectMapper.readValue(record.value(), ItemRented.class);
        savePointUsecase.savePoint(IDName.create(itemRented.getIdName().getId(),itemRented.getIdName().getName()), itemRented.getPoint());
    }

    @KafkaListener(topics = "${consumer.topic2.name}", groupId = "${consumer.groupId.name}")
    public void consumeReturn(ConsumerRecord<String, String> record) throws JsonProcessingException {
        log.info("rental_return: {}", record.value());
        ItemReturned itemReturned = objectMapper.readValue(record.value(), ItemReturned.class);
        savePointUsecase.savePoint(IDName.create(itemReturned.getIdName().getId(),itemReturned.getIdName().getName()), itemReturned.getPoint());
    }

    @KafkaListener(topics = "${consumer.topic3.name}", groupId = "${consumer.groupId.name}")
    public void consumeClear(ConsumerRecord<String, String> record) throws JsonProcessingException {
        log.info("overdue_cleared: {}", record.value());
        OverdueCleared overdueCleared = objectMapper.readValue(record.value(), OverdueCleared.class);

        EventResult eventResult = new EventResult();
        eventResult.setEventType(EventType.OVERDUE);
        eventResult.setItem(new Item());
        eventResult.setIdName(overdueCleared.getIdName());
        eventResult.setPoint(overdueCleared.getPoint());

        try {
            usePointUsecase.usePoint(IDName.create(overdueCleared.getIdName().getId(),overdueCleared.getIdName().getName()), overdueCleared.getPoint());
            eventResult.setSuccess(true);
        } catch (Exception e) {
            eventResult.setSuccess(false);
        }

        memberEventProducers.produce(eventResult);
    }

    @KafkaListener(topics = "${consumer.topic4.name}", groupId = "${consumer.groupId.name}")
    public void consumeUsePoint(ConsumerRecord<String, String> record) throws JsonProcessingException {
        log.info("use_point: {}", record.value());
        PointUse pointUse = objectMapper.readValue(record.value(), PointUse.class);

        try {
            usePointUsecase.usePoint(IDName.create(pointUse.getIdName().getId(),pointUse.getIdName().getName()), pointUse.getPoint());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}