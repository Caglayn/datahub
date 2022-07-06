package com.c8n.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataSourceDto {
    private String ipAddress;
    private String port;
    private String dbName;
    private String userName;
    private String password;
}
