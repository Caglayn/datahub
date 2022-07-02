package com.c8n.service;

import com.c8n.model.response.QueryResponse;

public interface BasicDataQueryParsingService {
    QueryResponse parseQuery(String query);
}
