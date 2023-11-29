package com.example.book.adapter.out.kafka;

import com.example.book.domain.model.event.EventResult;
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
public class BookEventProducers {

    @Value("${producers.topic1.name}")
    private String TOPIC;

    private final KafkaTemplate<String, EventResult> kafkaTemplate;

    public void produce(EventResult event) {
        this.kafkaTemplate
                .send(TOPIC, event)
                .addCallback(new ListenableFutureCallback<>() {

                    @Override
                    public void onSuccess(SendResult<String, EventResult> result) {
                        EventResult value = result.getProducerRecord().value();
                        log.info("Send message=[{}] with offset=[{}]", value.getItem().getNo(), result.getRecordMetadata().offset());
                    }

                    @Override
                    public void onFailure(Throwable ex) {
                        log.error("Unable to send message=[{}] due to : {}", event.getItem().getNo(), ex.getMessage(), ex);
                    }
                });
    }

}
