package advanced.offset;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.record.TimestampType;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Iterator;
import java.util.Optional;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * created by Joshua.H.Brooks on 2020.10月.22.09.41
 */
public class MyAutoCommitConfig {
    public static void main(String[] args) throws InterruptedException {
        consume();
    }

    private static void consume() {
        // Step 1: kafka参数配置
        Properties properties = new Properties();
        properties.setProperty(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "Node01:9092,Node02:9092,Node03:9092");
        //消息接收到后要进行反序列化解析
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //消费者要指明属于哪一个消费者组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"g3");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,true);
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,10000);

       // properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"true"); //
       // properties.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000");
        // 2. 消费者创建
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        // 3. 订阅/消费 Message
        consumer.subscribe(Pattern.compile("topicB"));
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
