package com.pt.umbrella.orderenricher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pt.umbrella.orderenricher.domain.Customer;
import com.pt.umbrella.orderenricher.domain.Product;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KeyValue;

import java.util.List;
import java.util.Properties;

public class Producers {

    public static void main(String[] args) throws JsonProcessingException {

        // create instance for properties to access producer configs
        Properties properties = new Properties();
        //Assign localhost id
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        //Set acknowledgements for producer requests.
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        //If the request fails, the producer can automatically retry,
        properties.put(ProducerConfig.RETRIES_CONFIG, 3);
        //Specify buffer size in config
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        //Reduce the no of requests less than 0
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");

        var objectMapper = new ObjectMapper();

        generateProducts(properties, objectMapper);
        generateCustomers(properties, objectMapper);
    }

    private static void generateProducts(Properties properties, ObjectMapper objectMapper) throws JsonProcessingException {
        var topicName = "outbox.event.Product";

        var products = List.of(
                new KeyValue<>("1", objectMapper.writeValueAsString(new Product(1, "Product A"))),
                new KeyValue<>("2", objectMapper.writeValueAsString(new Product(2, "Product B"))),
                new KeyValue<>("3", objectMapper.writeValueAsString(new Product(3, "Product C")))
        );

        sendKafkaRecord(properties, topicName, products);
    }

    private static void generateCustomers(Properties properties, ObjectMapper objectMapper) throws JsonProcessingException {
        var topicName = "outbox.event.Customer";

        var customers = List.of(
                new KeyValue<>("1", objectMapper.writeValueAsString(new Customer(1, "Camioes e Onibus"))),
                new KeyValue<>("2", objectMapper.writeValueAsString(new Customer(2, "AlvaGina e Camioes"))),
                new KeyValue<>("3", objectMapper.writeValueAsString(new Customer(3, "Kash Register e Camioes")))
        );

        sendKafkaRecord(properties, topicName, customers);
    }

    private static void sendKafkaRecord(Properties properties, String topicName, List<KeyValue<String, String>> records) {
        for (KeyValue<String, String> keyValue : records) {
            try (var producer = new KafkaProducer<String, String>(properties)) {
                var producerRecord = new ProducerRecord<>(topicName, keyValue.key, keyValue.value);
                producer.send(producerRecord, (recordMetadata, e) -> System.out.println("Record Sent message: " + keyValue));
            }
        }
    }
}



