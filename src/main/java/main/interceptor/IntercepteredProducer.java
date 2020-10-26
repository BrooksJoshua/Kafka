package main.interceptor;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * created by Joshua.H.Brooks on 2020.10月.22.12.48
 */
public class IntercepteredProducer {
    public static void main(String[] args) throws InterruptedException {
         produce();
    }
    private static void produce() throws InterruptedException {
        // 和topic API一样, 生产者消费者的API首先也是要指明配置参数, 即kafka的基本配置信息
        // Step 1: kafka参数配置
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "Node01:9092,Node02:9092,Node03:9092");
        //消息在进行网络传输的过程中要进行序列化
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG,UserDeinedInterceptor.class.getName());
        // 2. 生产者创建
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        for (int i = 1; i < 15; i++) {
            // 创建producerrecord
            ProducerRecord<String, String> producerRecord =
                    //new ProducerRecord<>("topic2" , "key" + i, String.valueOf(i));
                    new ProducerRecord<>("topic6" ,  String.valueOf(i));
            // 发送record
            producer.send(producerRecord);
          //  Thread.sleep(1000);
        }
        producer.close();


    }
}
