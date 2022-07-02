package com.c8n.service;

import com.c8n.model.dto.UpdateRowDto;

public interface BasicDataIndexService {
    boolean addIndexToCollection(String collectionName, String columnName);
    boolean deleteIndexFromCollection(String collectionName, String columnName);
    boolean isColumnHaveIndex(String collectionName, String columnName);
    boolean updateIndexByCollectionAndColumn(String collectionName, String columnName);
    boolean updateIndexesByCollectionName(String collectionName);
    boolean updateAllIndexes();
    boolean initializeAllIndexesOnStartUp();
    boolean clearAllIndexes();
    boolean truncateIndexesByCollectionName(String collectionName);
    boolean removeCollection(String collectionName);
    boolean addRowToCollection(UpdateRowDto row);
    boolean removeRowFromCollection(UpdateRowDto row);
    boolean updateRowFromCollection(UpdateRowDto newRow, UpdateRowDto oldRow);
}
