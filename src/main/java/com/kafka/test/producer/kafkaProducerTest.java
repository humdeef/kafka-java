package com.kafka.test.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.test.model.Model;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * 生产端
 *   Created by humdeef on 2017/1/8.
 */
public class kafkaProducerTest {
  public static void main(String[] args) throws Exception {
    //生产者
    Properties props = new Properties();
    props.put("bootstrap.servers", "192.168.25.156:9092");
    props.put("acks", "all");
    props.put("retries", 0);
    props.put("batch.size", 16384);
    props.put("linger.ms", 1);
    props.put("buffer.memory", 33554432);
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    Producer<String, String> producer = new KafkaProducer<String,String>(props);
    Model model = new Model();
    model.setId(2);
    model.setName("Tom");
    producer.send(new ProducerRecord<String, String>("topic1", Integer.toString(1), model.toJson()));
    producer.close();
  }
}

