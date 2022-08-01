import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProducerWithCustomPartitioner {
    public static void main(String[] args) {

        KafkaProducer<String, String> producer = KafkaConfig.customPartitionerSetup();

        ProducerRecord<String, String> record =
                new ProducerRecord<>(KafkaConfig.TOPIC_NAME, "custom-pt", "custom-pt");

        producer.send(record);
        producer.flush();
        producer.close();
    }
}
