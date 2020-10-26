package main.serialize;

import main.partition.UserDefinedPartitioner;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Date;
import java.util.Properties;

/**
 * created by Joshua.H.Brooks on 2020.10月.23.08.31
 */
public class Sender { // data needs serialization before sent
    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"Node01:9092,Node02:9092,Node03:9092");
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,UserDefinedSerializer.class.getName()); //指定value序列化使用的类
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.PARTITIONER_CLASS_CONFIG, UserDefinedPartitioner.class.getName());

        KafkaProducer<String, User> sender = new KafkaProducer<String, User>(properties);

        for (int i = 0; i < 9; i++) {
            User user = new User(1, "Elo_" + i, new Date());

            ProducerRecord<String, User> msg = new ProducerRecord<String, User>("topic3",String.valueOf(i),user);
            System.out.println("User"+i+"\t was just sent....");
            sender.send(msg);
        }
        sender.close();
    }
}
