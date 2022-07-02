package com.c8n.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryResponse {
    // 1 -> Command
    // 2 -> Query
    private int operation;
    private boolean isSuccess;
    private String collectionName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Map<String, String>> queryResult;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<String> fields;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long rowCount;
}
