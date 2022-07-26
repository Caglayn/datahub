package com.c8n.testutils;

import com.c8n.model.BasicDataHub;
import com.c8n.model.BasicDataIndex;
import com.c8n.model.entity.BasicData;

import java.util.*;

public class TestHubUtil {
    public static BasicDataHub getHub(){
        BasicDataHub hub = BasicDataHub.builder().hub(new TreeMap<>(String.CASE_INSENSITIVE_ORDER)).indexes(new TreeMap<>()).build();
        hub.getHub().put("test", BasicData.builder()
                .id(UUID.randomUUID())
                .collectionName("test")
                .createdDate(System.currentTimeMillis())
                .columns(new TreeSet<>())
                .collection(new TreeMap<>())
                .indexedColumns(new HashSet<>())
                .build());

        hub.getHub().get("test").getColumns().add("col1");
        hub.getHub().get("test").getColumns().add("col2");
        hub.getHub().get("test").getColumns().add("col3");
        hub.getHub().get("test").getColumns().add("col4");
        hub.getHub().get("test").getColumns().add("col5");

        Map<String, String> row = new HashMap<>();
        row.put("col1", "val1");
        row.put("col2", "val2");
        row.put("col3", "val3");
        row.put("col4", "val4");
        row.put("col5", "val5");

        String rowId = UUID.randomUUID().toString();

        hub.getHub().get("test").getCollection().put(rowId, row);

        hub.getHub().get("test").getIndexedColumns().add("col1");

        Map<String, TreeMap<String, Set<String>>> index = new HashMap<>();
        index.put("col1", new TreeMap<>());
        index.get("col1").put("val1", new HashSet<>(Collections.singletonList(rowId)));


        hub.getIndexes().put("test", BasicDataIndex.builder().collectionName("test").index(index).build());

        return hub;
    }
}
