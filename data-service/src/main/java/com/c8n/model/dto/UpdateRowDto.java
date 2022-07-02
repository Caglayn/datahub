package com.c8n.model.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateRowDto {
    private String collectionName;
    private String rowId;
    private Map<String, String> row;
}
