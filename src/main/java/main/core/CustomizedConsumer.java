package main.core;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.record.TimestampType;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;
import java.util.regex.Pattern;

/**
 * created by Joshua.H.Brooks on 2020.10月.22.14.21
 */
public class CustomizedConsumer {
    public static void main(String[] args) {
        consumeCustomized();
    }
    private static void consumeCustomized() {
        // Step 1: kafka参数配置
        Properties properties = new Properties();
        properties.setProperty(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "Node01:9092,Node02:9092,Node03:9092");
        //消息接收到后要进行反序列化解析
        properties.setProperty(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //消费者要指明属于哪一个消费者组
        //properties.put(ConsumerConfig.GROUP_ID_CONFIG,"g1");
        /**
         * 不指定消费者所属的消费者组, 而是指定
         */


        // 2. 消费者创建
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        // 3. 订阅/消费 Message
       // consumer.subscribe(Pattern.compile("^topic.*"));
        TopicPartition topic2_partition2 = new TopicPartition("topic2", 2);
        List<TopicPartition> list = Arrays.asList(topic2_partition2);
        consumer.assign(list);
        //指定消费分区的位置
       //  consumer.seekToBeginning(list); 从开始位置消费
        consumer.seek(topic2_partition2,1140);
        System.out.println("自定义消费topic, 指定topic的指定partition的指定offset开始消费.............");
        // 遍历消息队列
        while(true){
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
            if(!consumerRecords.isEmpty()){
                Iterator<ConsumerRecord<String, String>> iterator = consumerRecords.iterator();
                while (iterator.hasNext()){
                    ConsumerRecord<String, String> next = iterator.next();
                    String topic = next.topic();//获取下一条消息
                    int partition = next.partition();//获取该消息所属分区信息
                    String key = next.key();//获取消息key
                    String value = next.value(); //获取消息的value
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
