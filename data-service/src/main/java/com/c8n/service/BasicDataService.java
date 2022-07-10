package com.c8n.service;

import com.c8n.model.dto.UpdateRowDto;
import com.c8n.model.entity.BasicData;

import java.util.Map;
import java.util.Set;

public interface BasicDataService {
    String addnewRow(UpdateRowDto rowDto);
    boolean updateRow(UpdateRowDto rowDto);
    Map<String, String> deleteRow(String collectionName, String rowId);
    Map<String, String> getRow(String collectionName, String rowId);
    Map<String, Map<String, String>> getAllRows(String collectionName);
    boolean addColumns(String collectionName, Set<String> columns);
    boolean addAllColumns(String collectionName, Set<String> columns);
    boolean deleteColumn(String collectionName, String columnName);
    Set<String> getAllColumns(String collectionName);
}
