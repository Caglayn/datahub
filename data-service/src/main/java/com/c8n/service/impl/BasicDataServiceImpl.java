package com.c8n.service.impl;

import com.c8n.constants.CollectionStatus;
import com.c8n.exception.RowNotFound;
import com.c8n.model.BasicDataHub;
import com.c8n.model.dto.UpdateRowDto;
import com.c8n.service.BasicDataIndexService;
import com.c8n.service.BasicDataService;
import com.c8n.util.HubUtil;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class BasicDataServiceImpl implements BasicDataService {
    private final BasicDataHub basicDataHub;
    private final BasicDataIndexService indexService;
    private final HubUtil hubUtil;

    public BasicDataServiceImpl(BasicDataHub basicDataHub, BasicDataIndexService indexService, HubUtil hubUtil) {
        this.basicDataHub = basicDataHub;
        this.indexService = indexService;
        this.hubUtil = hubUtil;
    }

    @Override
    public String addnewRow(UpdateRowDto rowDto) {
        hubUtil.checkCollectionExist(rowDto.getCollectionName().toLowerCase());

        String rowId = UUID.randomUUID().toString().toLowerCase();
        rowDto.setRowId(rowId);
        basicDataHub.getHub().get(rowDto.getCollectionName().toLowerCase()).getCollection().put(rowId, rowDto.getRow());
        indexService.addRowToCollection(rowDto);
        return rowId;
    }

    @Override
    public boolean updateRow(UpdateRowDto rowDto) {
        hubUtil.checkCollectionExist(rowDto.getCollectionName().toLowerCase());

        Map<String, String> old = basicDataHub.getHub().get(rowDto.getCollectionName().toLowerCase()).getCollection().get(rowDto.getRowId().toLowerCase());

        if (old == null)
            throw new RowNotFound(CollectionStatus.NOT_FOUND.getCode(), rowDto.getCollectionName(), rowDto.getRowId());

        basicDataHub.getHub().get(rowDto.getCollectionName().toLowerCase()).getCollection().put(rowDto.getRowId().toLowerCase(), rowDto.getRow());
        indexService.updateRowFromCollection(rowDto, UpdateRowDto.builder().row(old).build());
        return true;
    }

    @Override
    public Map<String, String> deleteRow(String collectionName, String rowId) {
        hubUtil.checkCollectionExist(collectionName.toLowerCase());

        Map<String, String> row = basicDataHub.getHub().get(collectionName.toLowerCase()).getCollection().get(rowId.toLowerCase());

        if (row == null){
            throw new RowNotFound(CollectionStatus.NOT_FOUND.getCode(), collectionName.toLowerCase(), rowId.toLowerCase());
        }

        UpdateRowDto rowDto = UpdateRowDto.builder().collectionName(collectionName.toLowerCase()).rowId(rowId.toLowerCase()).row(row).build();
        indexService.removeRowFromCollection(rowDto);
        basicDataHub.getHub().get(collectionName.toLowerCase()).getCollection().remove(rowId.toLowerCase());
        return row;
    }

    @Override
    public Map<String, String> getRow(String collectionName, String rowId) {
        hubUtil.checkCollectionExist(collectionName.toLowerCase());

        return basicDataHub.getHub().get(collectionName.toLowerCase()).getCollection().get(rowId.toLowerCase());
    }

    @Override
    public Map<String, Map<String, String>> getAllRows(String collectionName) {
        hubUtil.checkCollectionExist(collectionName.toLowerCase());

        return basicDataHub.getHub().get(collectionName.toLowerCase()).getCollection();
    }

    @Override
    public boolean addColumns(String collectionName, Set<String> columns) {
        hubUtil.checkCollectionExist(collectionName.toLowerCase());

        for (String columnName : columns){
            if (columnName.trim().equals("null") || columnName.trim().equals(""))
                return false;
        }

        for (String columnNameFromHub : basicDataHub.getHub().get(collectionName.toLowerCase()).getColumns()){
            for (String columnNameFromRequest : columns){
                if (columnNameFromHub.equalsIgnoreCase(columnNameFromRequest))
                    return false;
            }
        }

        columns.forEach(i -> basicDataHub.getHub().get(collectionName.toLowerCase()).getColumns().add(i.toLowerCase()));
        return true;
    }

    @Override
    public boolean addAllColumns(String collectionName, Set<String> columns) {
        hubUtil.checkCollectionExist(collectionName.toLowerCase());

        basicDataHub.getHub().get(collectionName.toLowerCase()).getColumns().addAll(columns);

        return true;
    }

    @Override
    public boolean deleteColumn(String collectionName, String columnName) {
        hubUtil.checkCollectionAndColumnExist(collectionName.toLowerCase(), columnName.toLowerCase());

        if (indexService.isColumnHaveIndex(collectionName.toLowerCase(), columnName.toLowerCase()))
            indexService.deleteIndexFromCollection(collectionName.toLowerCase(), columnName.toLowerCase());

        basicDataHub.getHub().get(collectionName.toLowerCase()).getCollection().values().forEach(i -> i.remove(columnName.toLowerCase()));

        return basicDataHub.getHub().get(collectionName.toLowerCase()).getColumns().remove(columnName.toLowerCase());
    }

    @Override
    public Set<String> getAllColumns(String collectionName) {
        hubUtil.checkCollectionExist(collectionName.toLowerCase());

        return basicDataHub.getHub().get(collectionName.toLowerCase()).getColumns();
    }
}
