package com.c8n.service.query;

import com.c8n.model.response.QueryResponse;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public interface QueryService {
    QueryResponse executeSelectQuery(Set<String> selectFields, String collectionName, Map<String, ArrayList<String>> whereConditions);
}
