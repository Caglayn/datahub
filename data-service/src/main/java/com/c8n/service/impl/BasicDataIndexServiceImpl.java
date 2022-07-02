package com.c8n.service.impl;

import com.c8n.model.BasicDataHub;
import com.c8n.model.BasicDataIndex;
import com.c8n.model.dto.UpdateRowDto;
import com.c8n.model.entity.BasicData;
import com.c8n.service.BasicDataIndexService;
import com.c8n.util.HubUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.TreeMap;

@Service
@Slf4j
public class BasicDataIndexServiceImpl implements BasicDataIndexService {
    private final BasicDataHub basicDataHub;
    private final HubUtil hubUtil;

    public BasicDataIndexServiceImpl(BasicDataHub basicDataHub, HubUtil hubUtil) {
        this.basicDataHub = basicDataHub;
        this.hubUtil = hubUtil;
    }

    @Override
    public boolean addIndexToCollection(String collectionName, String columnName) {
        hubUtil.checkCollectionAndColumnExist(collectionName, columnName);

        basicDataHub.getIndexes().putIfAbsent(collectionName.toLowerCase(),
                BasicDataIndex
                        .builder()
                        .collectionName(collectionName.toLowerCase())
                        .index(new TreeMap<>())
                        .build());

        basicDataHub.getHub().get(collectionName.toLowerCase()).getIndexedColumns().add(columnName.toLowerCase());

        return updateIndexByCollectionAndColumn(collectionName.toLowerCase(), columnName.toLowerCase());
    }

    @Override
    public boolean deleteIndexFromCollection(String collectionName, String columnName) {
        hubUtil.checkIndexExist(collectionName, columnName);

        basicDataHub.getHub().get(collectionName.toLowerCase()).getIndexedColumns().remove(columnName.toLowerCase());
        basicDataHub.getIndexes().get(collectionName.toLowerCase()).getIndex().remove(columnName.toLowerCase());

        return true;
    }

    @Override
    public boolean isColumnHaveIndex(String collectionName, String columnName) {
        hubUtil.checkCollectionAndColumnExist(collectionName, columnName);
        return basicDataHub.getHub().get(collectionName.toLowerCase()).getIndexedColumns().contains(columnName.toLowerCase());
    }

    @Override
    public boolean updateIndexByCollectionAndColumn(String collectionName, String columnName) {
        hubUtil.checkIndexExist(collectionName, columnName);

        basicDataHub.getIndexes().get(collectionName.toLowerCase()).getIndex().put(columnName.toLowerCase(), new TreeMap<>(String.CASE_INSENSITIVE_ORDER));

        for (String rowId : basicDataHub.getHub().get(collectionName.toLowerCase()).getCollection().keySet()){
            String key = basicDataHub.getHub().get(collectionName.toLowerCase()).getCollection().get(rowId).get(columnName.toLowerCase());

            if (key == null || key.trim().length() == 0)
                key = "null";


            basicDataHub.getIndexes().get(collectionName.toLowerCase()).getIndex().get(columnName.toLowerCase())
                    .putIfAbsent(key, new HashSet<>());

            basicDataHub.getIndexes().get(collectionName.toLowerCase()).getIndex().get(columnName.toLowerCase()).get(key).add(rowId);
        }

        return true;
    }

    @Override
    public boolean updateIndexesByCollectionName(String collectionName) {
        hubUtil.checkCollectionExist(collectionName);
        log.info("Starting to update indexes for collection : " + collectionName.toLowerCase());
        for (String columnName : basicDataHub.getHub().get(collectionName.toLowerCase()).getIndexedColumns()){
            updateIndexByCollectionAndColumn(collectionName.toLowerCase(), columnName.toLowerCase());
        }
        log.info("All indexes updated for collection : " + collectionName.toLowerCase());
        return true;
    }

    @Override
    public boolean updateAllIndexes() {
        for (String collectionName : basicDataHub.getHub().keySet()){
            updateIndexesByCollectionName(collectionName);
        }
        return true;
    }

    @Override
    public boolean initializeAllIndexesOnStartUp() {
        log.info("Starting to initialize indexes...");
        for (BasicData collection : basicDataHub.getHub().values()){
            for (String indexColumnName : collection.getIndexedColumns()){
                addIndexToCollection(collection.getCollectionName().toLowerCase(), indexColumnName.toLowerCase());
            }
        }
        log.info("All indexes initialized.");
        return true;
    }

    @Override
    public boolean clearAllIndexes() {
        basicDataHub.getIndexes().clear();
        return true;
    }

    @Override
    public boolean truncateIndexesByCollectionName(String collectionName) {
        for (String indexedColumnName : basicDataHub.getIndexes().get(collectionName).getIndex().keySet()){
            basicDataHub.getIndexes().get(collectionName).getIndex().get(indexedColumnName).clear();
        }
        return true;
    }

    @Override
    public boolean removeCollection(String collectionName) {
        basicDataHub.getIndexes().remove(collectionName.toLowerCase());
        return true;
    }

    @Override
    public boolean addRowToCollection(UpdateRowDto row) {
        for(String indexedColumnName : basicDataHub.getHub().get(row.getCollectionName().toLowerCase()).getIndexedColumns()){
            String key = row.getRow().get(indexedColumnName);

            addKeyToIndex(row.getCollectionName(), indexedColumnName, key, row.getRowId());
        }

        return true;
    }

    @Override
    public boolean removeRowFromCollection(UpdateRowDto row) {
        for (String indexedColumnName : basicDataHub.getHub().get(row.getCollectionName().toLowerCase()).getIndexedColumns()){
            String key = row.getRow().get(indexedColumnName);

            removeKeyFromIndex(row.getCollectionName(), indexedColumnName, key, row.getRowId());
        }
        return true;
    }

    @Override
    public boolean updateRowFromCollection(UpdateRowDto newRow, UpdateRowDto oldRow) {
        for (String indexedColumnName : basicDataHub.getHub().get(newRow.getCollectionName().toLowerCase()).getIndexedColumns()){
            if (newRow.getRow().get(indexedColumnName.toLowerCase())==null || oldRow.getRow().get(indexedColumnName.toLowerCase())==null
                    || !newRow.getRow().get(indexedColumnName.toLowerCase()).equalsIgnoreCase(oldRow.getRow().get(indexedColumnName.toLowerCase()))){
                String oldKey = oldRow.getRow().get(indexedColumnName);
                String newKey = newRow.getRow().get(indexedColumnName);
                removeKeyFromIndex(newRow.getCollectionName(), indexedColumnName, oldKey, newRow.getRowId());
                addKeyToIndex(newRow.getCollectionName(), indexedColumnName, newKey, newRow.getRowId());
            }
        }

        return true;
    }

    private void addKeyToIndex(String collectionName, String columnName, String key, String rowId) {
        key = setKeyDefaultIfKeyNullOrEmpty(key);

        basicDataHub.getIndexes().get(collectionName.toLowerCase()).getIndex().get(columnName.toLowerCase()).putIfAbsent(key, new HashSet<>());
        basicDataHub.getIndexes().get(collectionName.toLowerCase()).getIndex().get(columnName.toLowerCase()).get(key).add(rowId.toLowerCase());
    }

    private void removeKeyFromIndex(String collectionName, String columnName, String key, String rowId) {
        key = setKeyDefaultIfKeyNullOrEmpty(key);

        basicDataHub.getIndexes().get(collectionName.toLowerCase()).getIndex().get(columnName.toLowerCase()).get(key).remove(rowId);
        if (basicDataHub.getIndexes().get(collectionName.toLowerCase()).getIndex().get(columnName.toLowerCase()).get(key).size() == 0)
            basicDataHub.getIndexes().get(collectionName.toLowerCase()).getIndex().get(columnName.toLowerCase()).remove(key);
    }

    private String setKeyDefaultIfKeyNullOrEmpty(String key){
        if (key == null || key.trim().length()==0)
            return  "null";
        else
            return key.toLowerCase();
    }
}
