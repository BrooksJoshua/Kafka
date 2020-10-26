package main.partition;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * created by Joshua.H.Brooks on 2020.10月.22.17.23
 */
public class UserDefinedPartitioner implements Partitioner {
    private AtomicInteger counter=new AtomicInteger(0);



    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        // 获取所有分区
        List<PartitionInfo> partitionInfos = cluster.partitionsForTopic(topic);

        // 获取分区数
        int partitionSize = partitionInfos.size();
        //
        if(keyBytes==null){
            int andIncrement = counter.getAndIncrement();
            return Utils.toPositive(andIncrement) % partitionSize;  //(andIncrement & Integer.MAX_VALUE) 和 Utils.toPositive(andIncrement) 的效果和 一样, 使andIncrement变为正数
        }else{
            return (Integer.parseInt((String) key) % partitionSize) ;
        }

    }

    @Override
    public void close() {
        System.out.println("self partition closing");
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
