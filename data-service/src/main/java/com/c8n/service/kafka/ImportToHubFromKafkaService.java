package com.c8n.service.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ImportToHubFromKafkaService {
    void importRowFromKafka(ConsumerRecord<String, String> consumerRecord);
}
