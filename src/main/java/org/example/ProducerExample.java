package org.example;

import org.apache.kafka.clients.producer.*;


import java.util.Properties;

public class ProducerExample {
    public static void main(String[] args) {
        Properties props = new Properties();

        // Address of Kafka broker
        props.put("bootstrap.servers", "192.168.29.215:9092");

        // kafka sends bytes internally so we need to tell it how to convert our data to bytes.
        // Convert key to bytes
        props.put("key.serializer", 
            "org.apache.kafka.common.serialization.StringSerializer");

        // Convert value to bytes
        props.put("value.serializer", 
            "org.apache.kafka.common.serialization.StringSerializer");

        // Step 2: Create producer instance
        Producer<String, String> producer = new KafkaProducer<>(props);

        //This means:

        // Send value "Hello Kafka!"
        // to topic "test-topic"
        // Partition is automatically chosen.
        // Step 3: Create message
        int i=0;
        while (true) {
            ProducerRecord<String, String> record =
                new ProducerRecord<>("test-topic", "Message " + i);

            producer.send(record);

            System.out.println("Sent: Message " + i);

            i++;

            try {
                Thread.sleep(1000); // 1 second delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // run using gradle runProducer
        }
    }
