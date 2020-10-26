package main.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;
import java.util.Random;

/**
 * created by Joshua.H.Brooks on 2020.10月.23.10.21
 */
public class UserDeinedInterceptor implements ProducerInterceptor {
    @Override
    public ProducerRecord onSend(ProducerRecord record) {
        //拦截器操作, 对消息做一些追加操作
        return new ProducerRecord(record.topic(),record.key(),record.value()+"追加_"+new Random().nextInt(10));
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        System.out.println("metadata:\t"+"{topic:"+metadata.topic()+",partition:"+metadata.partition()+",offset:"+metadata.offset()+",时间戳:"+metadata.timestamp()+",异常:"+exception+"}");
    }

    @Override
    public void close() {
        System.out.println("closing...");
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
