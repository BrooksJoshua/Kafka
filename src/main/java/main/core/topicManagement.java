package main.core;

import org.apache.kafka.clients.admin.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * created by Joshua.H.Brooks on 2020.10月.23.10.23
 */
public class topicManagement {
    private static  AdminClient adminClient;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties properties = prepareEnvironment();
        adminClient = KafkaAdminClient.create(properties);
        createTopicByMe(adminClient,"topicC");
        //listAllMyTopics(adminClient);
        //deleteTopicByMe(adminClient,"topicA");
        listAllMyTopics(adminClient,properties);
        adminClient.close();
    }

    private static void createTopicByMe(AdminClient adminClient,String topic2BCreated) throws ExecutionException, InterruptedException {
        CreateTopicsResult createTopicsResult =
                adminClient.createTopics(Arrays.asList(new NewTopic(topic2BCreated, 3, (short) 3)));
        createTopicsResult.all().get(); // 触发同步创建
    }

    private static void deleteTopicByMe(AdminClient adminClient,String topic2Bdeleted) throws ExecutionException, InterruptedException {
         DeleteTopicsResult deleTopicsResult = adminClient.deleteTopics(Arrays.asList(topic2Bdeleted));
          deleTopicsResult.all().get(); //触发同步删除
    }

    private static void listAllMyTopics(AdminClient adminClient,Properties properties) throws ExecutionException, InterruptedException {
        ListTopicsResult listTopicsResult = adminClient.listTopics();
        Set<String> topicNames = listTopicsResult.names().get();
        if(topicNames==null){
            System.out.println(properties.getProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG)+"is empty now, youcan publish topic-partitions to it l8r.");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String topic : topicNames) {
           // System.out.println(topic);
            stringBuilder.append(topic+",");
        }
        // 查看topic的详细信息
        String s = stringBuilder.toString();
        String substring = s.substring(0, s.length());
        String[] split = substring.split(",");

        DescribeTopicsResult describeTopics = adminClient.describeTopics(Arrays.asList(split));
        Map<String, TopicDescription> descMap = describeTopics.all().get();
        for (Map.Entry<String, TopicDescription> entry : descMap.entrySet()) {
            System.out.println("key:\t" + entry.getKey());
            System.out.println("value:\t" + entry.getValue());
        }
    }



    private static Properties prepareEnvironment() {
        Properties properties = new Properties();
        properties.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "Node01:9092,Node02:9092,Node03:9092");
        return properties;
    }

}
