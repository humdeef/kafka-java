package com.kafka.test.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

/**
 * kafka 消费端
 * Created by humdeef on 2017/1/8.
 */
public class KafkaConsumerTest {
    public static void main(String[] args) {
        //消费者
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.25.156:9092");//出现程序卡住，要配置host，如果是集群就需要配置全部服务器
        props.put("group.id", "one");

        props.put("enable.auto.commit", "false");//手动提交
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        new ConsumerThread(consumer).start();
    }
}
