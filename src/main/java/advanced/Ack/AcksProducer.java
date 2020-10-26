package advanced.Ack;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * created by Joshua.H.Brooks on 2020.10月.22.12.48
 */
public class AcksProducer {
    public static void main(String[] args) throws InterruptedException {
         produce();
    }
    private static void produce() throws InterruptedException {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "Node01:9092,Node02:9092,Node03:9092");

        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG,"1"); // 设置请求超时时间为1毫秒.
        properties.put(ProducerConfig.ACKS_CONFIG,"all"); // 设置确认应答机制为all, 即需要等待所有ISR副本确认
        properties.put(ProducerConfig.RETRIES_CONFIG,3); //设置重试3次, (不包含第一次)

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

            // 创建producerrecord
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("topicC" , "acks","ack test");
            // 发送record
            producer.send(producerRecord);
            //
            producer.flush();

        producer.close();
    }
}
