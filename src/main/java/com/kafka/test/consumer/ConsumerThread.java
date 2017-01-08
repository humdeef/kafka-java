package com.kafka.test.consumer;

import com.kafka.test.model.Model;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by humdeef on 2017/1/8.
 */
public class ConsumerThread extends Thread{

    private final Logger log = LoggerFactory.getLogger(ConsumerThread.class);

    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final KafkaConsumer consumer;

    public ConsumerThread(KafkaConsumer consumer){
        this.consumer = consumer;
    }

    public void run() {
        try {
            consumer.subscribe(Arrays.asList("topic1"));
            while (!closed.get()) {
                ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
                for (TopicPartition partition : records.partitions()) {
                    List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
                    long lastOffset = 0;
                    try {
                        for (ConsumerRecord<String, String> record : partitionRecords) {
                            System.out.println("value is "+record.value());
                            Model model = Model.toModel(record.value());
                            System.out.println("convert to model:{}"+model.toString());
                            lastOffset = record.offset();
                        }
                    }catch (Exception e) {
                        //TODO send error msg
                        if(log.isDebugEnabled()){
                            e.printStackTrace();
                        }
                        log.error(e.getMessage());
                    }
                    if(lastOffset > 0) {
                        consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));
                    }
                }
            }
        } catch (WakeupException e) {
            if (!closed.get()) throw e;
        } finally {
            consumer.close();
        }
    }

    // Shutdown hook which can be called from a separate thread
    public void shutdown() {
        closed.set(true);
        consumer.wakeup();
    }
}
