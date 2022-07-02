package com.c8n.model;

import com.c8n.model.entity.BasicData;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicDataHub {
    private Map<String, BasicData> hub;
    private Map<String, BasicDataIndex> indexes;
}
