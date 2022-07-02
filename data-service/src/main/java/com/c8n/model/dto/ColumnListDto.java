package com.c8n.model.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ColumnListDto {
    private String collectionName;
    private Set<String> columnNames;
}
