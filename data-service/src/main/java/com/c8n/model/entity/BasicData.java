package com.c8n.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(value = "collection_basic")
public class BasicData {
    @Id
    private UUID id;
    private String collectionName;
    private long createdDate;
    private Set<String> columns;
    private Set<String> indexedColumns;

    // Map<rowId, Map<columnName, data>>
    private Map<String, Map<String, String>> collection;

}
