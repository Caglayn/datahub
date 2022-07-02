package com.c8n.model.response;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuccessResponse {
    private int status;
    private String message;
    private Object body;
    private double elapsedTime;
}
