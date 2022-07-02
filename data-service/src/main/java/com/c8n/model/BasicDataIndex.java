package com.c8n.model;

import lombok.*;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicDataIndex {
    private String collectionName;
    private Map<String, TreeMap<String, Set<String>>> index;
}
