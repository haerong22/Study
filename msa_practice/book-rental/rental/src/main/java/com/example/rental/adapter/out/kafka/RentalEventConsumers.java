package com.example.rental.adapter.out.kafka;

import com.example.rental.application.port.in.CompensationUseCase;
import com.example.rental.domain.event.EventResult;
import com.example.rental.domain.event.EventType;
import com.example.rental.domain.model.vo.IDName;
import com.example.rental.domain.model.vo.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalEventConsumers {

    private final ObjectMapper objectMapper;
    private final CompensationUseCase compensationUseCase;

    @KafkaListener(topics = "${consumers.topic1.name}", groupId = "${consumers.groupId.name}")
    public void consumeRental(ConsumerRecord<String, String> record) {
        try {
            log.info("Consumer record: {}", record.value());

            EventResult eventResult = objectMapper.readValue(record.value(), EventResult.class);
            IDName idName = IDName.create(eventResult.getIdName().getId(), eventResult.getIdName().getName());
            Item item = Item.create(eventResult.getItem().getNo(), eventResult.getItem().getTitle());
            long point = eventResult.getPoint();

            if (!eventResult.isSuccess()) {
                EventType eventType = eventResult.getEventType();
                log.info("EventType = {}", eventType);
                switch (eventType) {
                    case RENT:
                        log.info("대여취소 보상트랜젝션 실행");
                        compensationUseCase.cancelRentItem(idName, item);
                        break;
                    case RETURN:
                        log.info("반납취소 보상트랜젝션 실행");
                        compensationUseCase.cancelReturnItem(idName, item, point);
                        break;
                    case OVERDUE:
                        log.info("연체해제처리취소 보상트랜젝션 실행");
                        compensationUseCase.cancelMakeAvailableRental(idName, point);
                        break;
                    default:
                        // 다른 이벤트 유형에 대한 처리를 여기에 추가
                }
                // 포인트 보상처리 (모든 경우에 공통)
            } else {
                log.info("eventResult.isSuccess() = {}", eventResult.isSuccess());
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
