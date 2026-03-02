package org.example;
import org.apache.kafka.clients.consumer.*;

import java.util.Collection;
import org.apache.kafka.common.TopicPartition;

import java.util.Collections;   
import java.util.Properties;
import java.time.Duration;

public class ConsumerExample {
    
    public static void main(String[] args) {
    
        Properties props = new Properties();

        // Address of Kafka broker
        props.put("bootstrap.servers", "192.168.29.215:9092");
        // here we specify which consumer group the consumer belongs to.
        props.put("group.id", "my-consumer-group");

        // Deserializer for key
        props.put("key.deserializer", 
            "org.apache.kafka.common.serialization.StringDeserializer");

        // Deserializer for value
        props.put("value.deserializer", 
            "org.apache.kafka.common.serialization.StringDeserializer");

        // inside partition messages are ordered and each message has an offset.
        // Offset 0 → A
        // Offset 1 → B
        // Offset 2 → C

        // Offset = position of message.
        // Consumer must remember:

        // 👉 “Up to which message have I processed?”

        // Otherwise after restart:

        // It may read from beginning again.

        // Or skip messages.
        // Automatically commit offsets
        //Kafka automatically saves your current offset periodically (default ~5 seconds).
        // So if consumer crashes:
        // It resumes from last committed offset.
        props.put("enable.auto.commit", "true");

        // Step 2: Create consumer
        KafkaConsumer<String, String> consumer =
                new KafkaConsumer<>(props);

        // Step 3: Subscribe to topic
       // consumer.subscribe(Collections.singletonList("test-topic"));

       // consumer.subscribe() but with code to observe how rebalancing works when multiple consumers are in the same group.
       consumer.subscribe(
            Collections.singletonList("test-topic"),
            new ConsumerRebalanceListener() {
                // Called when:
                // Consumer is about to lose partitions.
                // Happens before rebalance.
                @Override
                public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                    System.out.println("Partitions revoked: " + partitions);
                }
                // Called after rebalance.
                // Consumer receives new partitions.
                @Override
                public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                    System.out.println("Partitions assigned: " + partitions);
                }
                 @Override
                public void onPartitionsLost(Collection<TopicPartition> partitions) {
                    System.out.println("Partitions lost: " + partitions);
                }
    }
        );

        // Step 4: Poll loop (very important)
        while (true) {

            ConsumerRecords<String, String> records =
                    consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> record : records) {
                System.out.println("Received message: " + record.value());
            }
        }

        // Producer pushes data.
        // Consumer pulls data using poll().
        // Kafka does not push messages automatically.
        // run using gradle runConsumer
    }
}
