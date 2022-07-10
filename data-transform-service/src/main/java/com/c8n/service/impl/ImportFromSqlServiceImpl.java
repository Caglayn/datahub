package com.c8n.service.impl;

import com.c8n.service.ImportFromSqlService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ImportFromSqlServiceImpl implements ImportFromSqlService {
    private final JdbcTemplate jdbcTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${KAFKA_IMPORT_TOPIC}")
    private String importTopicName;

    public ImportFromSqlServiceImpl(JdbcTemplate jdbcTemplate, KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Map<String, Object>> importFromQuery(String collectionName, String queryString) {
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(queryString);
        for (Map<String, Object> row : resultList){
            try {
                String rowString = objectMapper.writeValueAsString(row);
                kafkaTemplate.send(importTopicName, collectionName, rowString);
            } catch (JsonProcessingException e) {
                log.error(e.getMessage());
            }
        }
        return resultList;
    }
}
