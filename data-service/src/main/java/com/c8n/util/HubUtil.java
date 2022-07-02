package com.c8n.util;

import com.c8n.constants.CollectionStatus;
import com.c8n.exception.CollectionNotFoundException;
import com.c8n.exception.ColumnNotFound;
import com.c8n.exception.IndexNotFound;
import com.c8n.model.BasicDataHub;
import org.springframework.stereotype.Component;

@Component
public class HubUtil {
    private final BasicDataHub basicDataHub;

    public HubUtil(BasicDataHub basicDataHub) {
        this.basicDataHub = basicDataHub;
    }

    public void checkCollectionExist(String collectionName){
        if (basicDataHub.getHub().get(collectionName.toLowerCase()) == null)
            throw new CollectionNotFoundException(CollectionStatus.NOT_FOUND.getCode(), collectionName.toLowerCase());
    }

    public void checkCollectionAndColumnExist(String collectionName, String columnName){
        this.checkCollectionExist(collectionName);
        if (!basicDataHub.getHub().get(collectionName.toLowerCase()).getColumns().contains(columnName.toLowerCase()))
            throw new ColumnNotFound(CollectionStatus.NOT_FOUND.getCode(), collectionName.toLowerCase(), columnName.toLowerCase());
    }

    public void checkIndexExist(String collectionName, String columnName){
        this.checkCollectionAndColumnExist(collectionName, columnName);
        if(!basicDataHub.getHub().get(collectionName.toLowerCase()).getIndexedColumns().contains(columnName.toLowerCase()))
            throw new IndexNotFound(CollectionStatus.INDEX_NOT_FOUND.getCode(), collectionName.toLowerCase(), columnName.toLowerCase());
    }
}
