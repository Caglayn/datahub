package com.c8n.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerConfiguration {
    private final KafkaProperties kafkaProperties;
    @Value("${SERVER_IP}")
    private String serverAddress;

    @Value("${SERVER_KAFKA_PORT}")
    private String serverKafkaPort;


    public ProducerConfiguration(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory(){
        Map<String, Object> properties = kafkaProperties.buildProducerProperties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, serverAddress + ":" + serverKafkaPort);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<String, String>(properties);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, serverAddress + ":" + serverKafkaPort);
        return new KafkaAdmin(configs);
    }

//    @Bean
//    public NewTopic topicImport(){
//        return TopicBuilder.name(importTopicName).partitions(1).replicas(1).build();
//    }
}
