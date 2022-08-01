import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;

public class ProducerWithKeyValue {

    public static void main(String[] args) {
        KafkaProducer<String, String> producer = KafkaConfig.setup();

        ProducerRecord<String, String> record = new ProducerRecord<>(KafkaConfig.TOPIC_NAME, "key1", "value1");
        producer.send(record);
        ProducerRecord<String, String> record2 = new ProducerRecord<>(KafkaConfig.TOPIC_NAME, "key2", "value2");
        producer.send(record2);
        producer.flush();
        producer.close();
    }
}
