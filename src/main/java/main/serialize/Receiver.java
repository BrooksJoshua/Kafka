package main.serialize;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.record.TimestampType;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.Properties;

/**
 * created by Joshua.H.Brooks on 2020.10月.23.08.39
 */
public class Receiver {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"Node01:9092,Node02:9092,Node03:9092");
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,UserDefinedDeSerializer.class.getName()); //指定value序列化使用的类
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"g1");
        KafkaConsumer<String, User> receiver = new KafkaConsumer<>(properties);
        receiver.subscribe(Arrays.asList("topic3")); //
        while(true){
            ConsumerRecords<String, User> records = receiver.poll(Duration.ofSeconds(1));
            if(!records.isEmpty()){
                Iterator<ConsumerRecord<String, User>> iterator = records.iterator();
                while(iterator.hasNext()){
                    ConsumerRecord<String, User> next = iterator.next();
                    String topic = next.topic();//获取下一条消息
                    int partition = next.partition();//获取该消息所属分区信息
                    String key = next.key();//获取消息key
                    User value = next.value(); //获取消息的value
                    long offset = next.offset();//获取消息的偏移量
                    long timestamp = next.timestamp();//获取消息的时间戳
                    TimestampType timestampType = next.timestampType(); // 获取消息的时间戳类型
                    Headers headers = next.headers();
                    Optional<Integer> leaderEpoch = next.leaderEpoch();
                    int serializedKeySize = next.serializedKeySize();
                    int serializedValueSize = next.serializedValueSize();
                    System.out.println(
                            "topic:\t"+topic+ "\t"+
                                    "key:\t"+key+"\t"+
                                    "value:\t"+value+"\t"+
                                    "offset:\t"+offset+"\t"+
                                    "partition:\t"+partition+"\t"+
                                    "timestamp:\t"+timestamp+"\t"

                    );
                }
            }


        }



    }


}
