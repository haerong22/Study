import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;

public class ProducerExactPartition {

    public static void main(String[] args) {
        KafkaProducer<String, String> producer = KafkaConfig.setup();

        int partitionNo = 0;
        ProducerRecord<String, String> record =
                new ProducerRecord<>(KafkaConfig.TOPIC_NAME, partitionNo, "partition", "partition");
        producer.send(record);

        producer.flush();
        producer.close();
    }
}
