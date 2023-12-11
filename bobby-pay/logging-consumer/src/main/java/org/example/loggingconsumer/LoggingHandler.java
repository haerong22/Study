package org.example.loggingconsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class LoggingHandler {

    private final KafkaConsumer<String, String> consumer;

    public LoggingHandler(KafkaConsumer<String, String> consumer) {
        this.consumer = consumer;
        this.consume();
    }

    public void consume() {
        Thread consumerThread = new Thread(() -> {
            try {
                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                    if (records == null) {
                        continue;
                    }
                    for (ConsumerRecord<String, String> record : records) {
                        handle(record);
                    }
                }
            } finally {
                consumer.close();
            }
        });
        consumerThread.start();
    }

    private void handle(ConsumerRecord<String, String> record) {
        if (!record.value().startsWith("[logging]")){
            return;
        }

        System.out.println("Received message: " + record.value());
    }
}
