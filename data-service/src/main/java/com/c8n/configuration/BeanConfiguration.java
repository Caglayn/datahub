package com.c8n.configuration;

import com.c8n.model.BasicDataHub;
import com.c8n.model.Sessions;
import com.c8n.util.TimeUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Configuration
public class BeanConfiguration {
    @Bean
    public BasicDataHub basicDataHub(){
        return BasicDataHub
                .builder()
                .hub(new TreeMap<>(String.CASE_INSENSITIVE_ORDER))
                .indexes(new TreeMap<>())
                .build();
    }

    @Bean
    @RequestScope
    public TimeUtil timer(){
        return TimeUtil.builder().build();
    }

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    @Bean
    public TypeReference<Map<String, String>> typeReference(){
        return new TypeReference<Map<String, String>>() {};
    }

    @Bean
    public Sessions sessions(){
        return Sessions.builder().users(new HashMap<>()).build();
    }
}
