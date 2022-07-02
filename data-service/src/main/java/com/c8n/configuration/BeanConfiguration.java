package com.c8n.configuration;

import com.c8n.model.BasicDataHub;
import com.c8n.util.TimeUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

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
}
