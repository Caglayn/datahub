package com.c8n.service;

import java.util.List;
import java.util.Map;

public interface ImportFromSqlService {
    List<Map<String, Object>> importFromQuery(String queryString);
}
