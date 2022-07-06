package com.c8n.service.impl;

import com.c8n.service.ImportFromSqlService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ImportFromSqlServiceImpl implements ImportFromSqlService {
    private final JdbcTemplate jdbcTemplate;

    public ImportFromSqlServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Map<String, Object>> importFromQuery(String queryString) {
        return jdbcTemplate.queryForList(queryString);
    }
}
