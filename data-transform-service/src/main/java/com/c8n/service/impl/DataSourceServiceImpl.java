package com.c8n.service.impl;

import com.c8n.model.dto.DataSourceDto;
import com.c8n.service.DataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import static com.c8n.constants.DbConstants.*;

@Service
@Slf4j
public class DataSourceServiceImpl implements DataSourceService {
    private final JdbcTemplate jdbcTemplate;
    private final DriverManagerDataSource dataSource;

    public DataSourceServiceImpl(JdbcTemplate jdbcTemplate, DriverManagerDataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    @Override
    public boolean changeDatasource(DataSourceDto dto) {
        dataSource.setUrl(POSTGRESURLPREFIX + dto.getIpAddress() + ":" + POSTGRESPORT + "/" + dto.getDbName());
        dataSource.setUsername(dto.getUserName());
        dataSource.setPassword(dto.getPassword());
        jdbcTemplate.setDataSource(dataSource);
        return isConnectionOk();
    }

    private boolean isConnectionOk(){
        try {
            return jdbcTemplate.queryForList("SELECT 1").size()>0;
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
