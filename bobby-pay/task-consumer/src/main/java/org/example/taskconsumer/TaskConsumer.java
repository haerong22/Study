package org.example.taskconsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.example.common.tasks.RechargingMoneyTask;
import org.example.common.tasks.SubTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Component
public class TaskConsumer {

    private final KafkaConsumer<String, String> consumer;
    private final TaskResultProducer taskResultProducer;
    private final ObjectMapper mapper;

    public TaskConsumer(
            @Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
            @Value("${task.topic}") String topic,
            TaskResultProducer taskResultProducer,
            ObjectMapper mapper
    ) {
        this.taskResultProducer = taskResultProducer;
        this.mapper = mapper;

        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", "my-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        this.consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));

        Thread consumerThread = new Thread(() -> {
            try {
                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                    for (ConsumerRecord<String, String> record : records) {

                        RechargingMoneyTask task;

                        try {
                            task = this.mapper.readValue(record.value(), RechargingMoneyTask.class);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }

                        for (SubTask subTask : task.getSubTaskList()) {

                            // task 수행

                            subTask.setStatus("success");
                        }

                        this.taskResultProducer.sendTaskResult(record.key(), task);
                    }
                }
            } finally {
                consumer.close();
            }
        });
        consumerThread.start();
    }
}