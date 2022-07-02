package com.c8n.service.query;

import com.c8n.constants.CollectionStatus;
import com.c8n.exception.ColumnNotFound;
import com.c8n.exception.UnrecognizedQuery;
import com.c8n.model.BasicDataHub;
import com.c8n.model.response.QueryResponse;
import com.c8n.service.BasicDataIndexService;
import com.c8n.util.HubUtil;
import org.springframework.stereotype.Service;

import static com.c8n.constants.QueryConstants.*;

import java.util.*;

@Service
public class QueryServiceImpl implements QueryService{
    private final BasicDataHub dataHub;
    private final HubUtil hubUtil;
    private final BasicDataIndexService indexService;

    public QueryServiceImpl(BasicDataHub dataHub, HubUtil hubUtil, BasicDataIndexService indexService) {
        this.dataHub = dataHub;
        this.hubUtil = hubUtil;
        this.indexService = indexService;
    }

    @Override
    public QueryResponse executeSelectQuery(Set<String> selectFields, String collectionName, Map<String, ArrayList<String>> whereConditions) {
        hubUtil.checkCollectionExist(collectionName);

        if (selectFields.contains("*"))
            selectFields = dataHub.getHub().get(collectionName).getColumns();
        else
            for (String selectField : selectFields){ // check all columns exist in hub
                if (!dataHub.getHub().get(collectionName).getColumns().contains(selectField))
                    throw new ColumnNotFound(CollectionStatus.NOT_FOUND.getCode(), collectionName, selectField);
            }

        if (whereConditions == null || whereConditions.isEmpty()){
            return selectWithoutWhereConditions(selectFields, collectionName);

        } else {
            return selectWithWhereConditions(selectFields, collectionName, whereConditions);
        }
    }

    private QueryResponse selectWithWhereConditions(Set<String> selectFields, String collectionName, Map<String, ArrayList<String>> whereConditions) {
        for (String field : whereConditions.get(FIELD)){
            if (!dataHub.getHub().get(collectionName).getColumns().contains(field))
                throw new ColumnNotFound(CollectionStatus.NOT_FOUND.getCode(), collectionName, field);
        }

        QueryResponse plainResponse = selectWithoutWhereConditions(selectFields, collectionName);

        for (int i = 0; i< whereConditions.get(SYMBOL).size(); i++){
            if (indexService.isColumnHaveIndex(collectionName, whereConditions.get(FIELD).get(i))){
                plainResponse // TODO convert here to indexedSearch
                        .setQueryResult(nonIndexedFilter(plainResponse.getQueryResult(), whereConditions.get(FIELD).get(i),
                                whereConditions.get(SYMBOL).get(i), whereConditions.get(VALUE).get(i)));
            } else {
                plainResponse
                        .setQueryResult(nonIndexedFilter(plainResponse.getQueryResult(), whereConditions.get(FIELD).get(i),
                                whereConditions.get(SYMBOL).get(i), whereConditions.get(VALUE).get(i)));
            }
        }

        plainResponse.setRowCount((long)plainResponse.getQueryResult().size());

        return plainResponse;
    }

    private QueryResponse selectWithoutWhereConditions(Set<String> selectFields, String collectionName){
        QueryResponse response = QueryResponse.builder().operation(2).queryResult(new TreeMap<>()).fields(selectFields).isSuccess(true).collectionName(collectionName).build();
        for (String rowId : dataHub.getHub().get(collectionName).getCollection().keySet()){
            Map<String, String> rowData = dataHub.getHub().get(collectionName).getCollection().get(rowId);
            response.getQueryResult().put(rowId, new HashMap<>());
            selectFields.forEach(i -> response.getQueryResult().get(rowId).put(i, rowData.get(i)));
        }

        response.setRowCount((long)response.getQueryResult().size());
        return response;
    }

    private Map<String, Map<String, String>> nonIndexedFilter(Map<String, Map<String, String>> rows, String filterField, String symbol, String value){
        Map<String, Map<String, String>> filteredRows = new TreeMap<>();

        for (String rowId : rows.keySet()){
            if (compareBySymbol(rows.get(rowId).get(filterField), symbol, value)){
                filteredRows.put(rowId, rows.get(rowId));
            }
        }

        return filteredRows;
    }

    private boolean compareBySymbol(String filterFieldValue, String symbol, String comparingValue){
        return switch (symbol){
            case GREATER -> Long.parseLong(filterFieldValue) > Long.parseLong(comparingValue);
            case LESS -> Long.parseLong(filterFieldValue) < Long.parseLong(comparingValue);
            case EQUAL -> filterFieldValue.equals(comparingValue);
            default -> throw new UnrecognizedQuery(CollectionStatus.QUERY_NOT_RECOGNIZED.getCode());
        };
    }
}
