package com.c8n.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionResponse {
    private int statusCode;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> exceptionList;

    private ExceptionResponse addException(String name, String detail){
        if (exceptionList == null)
            exceptionList = new HashMap<>();
        exceptionList.put(name, detail);
        return this;
    }
}
