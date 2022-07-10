package com.c8n.configuration;

import static com.c8n.constants.DbConstants.*;

import com.c8n.util.TimeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class BeanConfiguration {
    @Value("${SERVER_IP}")
    private String serverIp;

    @Value("${POSTGRES_START_DB}")
    private String dbName;

    @Value("${POSTGRES_USER}")
    private String dbUserName;

    @Value("${POSTGRES_PASSWORD}")
    private String dbPassword;

    @Bean
    @Lazy
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public DriverManagerDataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(POSTGRESDRIVER);
        dataSource.setUrl(POSTGRESURLPREFIX + serverIp + ":" + POSTGRESPORT + "/" + dbName);
        dataSource.setUsername(dbUserName);
        dataSource.setPassword(dbPassword);

        return dataSource;
    }

    @Bean
    @Lazy
    public JdbcTemplate getJdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }

    @Bean
    @RequestScope
    public TimeUtil timer(){
        return TimeUtil.builder().build();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
