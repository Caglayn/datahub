package com.c8n.service.kafka;

import com.c8n.model.dto.UpdateRowDto;
import com.c8n.service.BasicDataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ImportToHubFromKafkaServiceImpl implements ImportToHubFromKafkaService{

    private final BasicDataService dataService;
    private final ObjectMapper objectMapper;
    private final TypeReference<Map<String, String>> typeReference;

    public ImportToHubFromKafkaServiceImpl(BasicDataService dataService, ObjectMapper objectMapper, TypeReference<Map<String, String>> typeReference) {
        this.dataService = dataService;
        this.objectMapper = objectMapper;
        this.typeReference = typeReference;
    }

    @KafkaListener(topics = "t-import")
    @Override
    public void importRowFromKafka(ConsumerRecord<String, String> consumerRecord) {
        Map<String, String> row = null;
        try {
            row = objectMapper.readValue(consumerRecord.value(), typeReference);
            dataService.addAllColumns(consumerRecord.key(), row.keySet());
            dataService.addnewRow(UpdateRowDto.builder().row(row).collectionName(consumerRecord.key()).build());
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}
