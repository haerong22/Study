package com.example.rental.adapter.out.kafka;

import com.example.rental.application.port.out.EventPort;
import com.example.rental.domain.event.ItemRented;
import com.example.rental.domain.event.ItemReturned;
import com.example.rental.domain.event.OverdueCleared;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalKafkaProducer implements EventPort {

    @Value("${producers.topic1.name}")
    private String TOPIC_RENT;

    @Value("${producers.topic2.name}")
    private String TOPIC_RETURN;

    @Value("${producers.topic3.name}")
    private String TOPIC_CLEAR;

    private final KafkaTemplate<String, ItemRented> kafkaTemplate1;
    private final KafkaTemplate<String, ItemReturned> kafkaTemplate2;
    private final KafkaTemplate<String, OverdueCleared> kafkaTemplate3;

    @Override
    public void rentalEvent(ItemRented event) {
        this.kafkaTemplate1
                .send(TOPIC_RENT, event)
                .addCallback(new ListenableFutureCallback<>() {

                    @Override
                    public void onSuccess(SendResult<String, ItemRented> result) {
                        ItemRented value = result.getProducerRecord().value();
                        log.info("Send message=[{}] with offset=[{}]", value.getItem().getNo(), result.getRecordMetadata().offset());
                    }

                    @Override
                    public void onFailure(Throwable ex) {
                        log.error("Unable to send message=[{}] due to : {}", event.getItem().getNo(), ex.getMessage(), ex);
                    }
                });
    }

    @Override
    public void returnEvent(ItemReturned event) {
        this.kafkaTemplate2
                .send(TOPIC_RETURN, event)
                .addCallback(new ListenableFutureCallback<>() {

                    @Override
                    public void onSuccess(SendResult<String, ItemReturned> result) {
                        ItemReturned value = result.getProducerRecord().value();
                        log.info("Send message=[{}] with offset=[{}]", value.getItem().getNo(), result.getRecordMetadata().offset());
                    }

                    @Override
                    public void onFailure(Throwable ex) {
                        log.error("Unable to send message=[{}] due to : {}", event.getItem().getNo(), ex.getMessage(), ex);
                    }
                });
    }

    @Override
    public void overdueClearEvent(OverdueCleared event) {
        this.kafkaTemplate3
                .send(TOPIC_CLEAR, event)
                .addCallback(new ListenableFutureCallback<>() {

                    @Override
                    public void onSuccess(SendResult<String, OverdueCleared> result) {
                        OverdueCleared value = result.getProducerRecord().value();
                        log.info("Send message=[{}] with offset=[{}]", value.getIdName().getId(), result.getRecordMetadata().offset());
                    }

                    @Override
                    public void onFailure(Throwable ex) {
                        log.error("Unable to send message=[{}] due to : {}", event.getIdName().getId(), ex.getMessage(), ex);
                    }
                });
    }
}
