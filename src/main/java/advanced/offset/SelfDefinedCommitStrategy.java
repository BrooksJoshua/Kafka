
package advanced.offset;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.record.TimestampType;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;
import java.util.regex.Pattern;

    public class SelfDefinedCommitStrategy {
        public static void main(String[] args) throws InterruptedException {
            consume();
        }

        private static void consume() {
            // Step 1: kafka参数配置
            Properties properties = new Properties();
            properties.setProperty(
                    ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "Node01:9092,Node02:9092,Node03:9092");
            properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            properties.put(ConsumerConfig.GROUP_ID_CONFIG,"g4");
            properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
            properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);
            // 2. 消费者创建
            KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
            // 3. 订阅/消费 Message
            consumer.subscribe(Pattern.compile("topicC"));
            // 遍历消息队列
            while(true){
                ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
                if(!consumerRecords.isEmpty()){
                    Iterator<ConsumerRecord<String, String>> iterator = consumerRecords.iterator();
                    //新建一个map集合来存储分区的offset信息, key是TopicPartition, 值是offset和元数据信息
                    Map<TopicPartition, OffsetAndMetadata> offsetMap=new HashMap<>();

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
                        //每次消费完之后都要提交offset和元数据信息, 也可以格局自己的业务需求去在合适的时机提交.
                        offsetMap.put(new TopicPartition(topic,partition),new OffsetAndMetadata(offset+1));
                        consumer.commitAsync(offsetMap, new OffsetCommitCallback() {
                            @Override
                            public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                                System.out.println("提交了如下offset&metadata信息: \r\n"+"offset:\t"+offset+"\texception(if any):\t"+exception);
                            }
                        });
                    }
                }
            }
        }

    }
