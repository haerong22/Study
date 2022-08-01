import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;

public class ProducerWithAsyncCallback {

    public static void main(String[] args) {

        KafkaProducer<String, String> producer = KafkaConfig.setup();

        ProducerRecord<String, String> record =
                new ProducerRecord<>(KafkaConfig.TOPIC_NAME, "async-callback", "async-callback");

        producer.send(record, new ProducerCallback());

        producer.flush();
        producer.close();
    }
}
