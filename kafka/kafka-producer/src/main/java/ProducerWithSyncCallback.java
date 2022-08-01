import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerWithSyncCallback {
    private final static Logger logger = LoggerFactory.getLogger(ProducerWithSyncCallback.class);

    public static void main(String[] args) {

        KafkaProducer<String, String> producer = KafkaConfig.setup();

        ProducerRecord<String, String> record =
                new ProducerRecord<>(KafkaConfig.TOPIC_NAME, "sync-callback", "sync-callback");
        try {
            RecordMetadata metadata = producer.send(record).get();
            logger.info(metadata.toString());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } finally {
            producer.flush();
            producer.close();
        }
    }
}
