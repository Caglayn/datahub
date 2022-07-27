package com.c8n.exception;

import com.c8n.model.response.ExceptionResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNameAlreadyInUse.class)
    @Order(value = -1)
    public ResponseEntity<ExceptionResponse> handleUserNameAlreadyInUseException(UserNameAlreadyInUse exception){
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(ExceptionResponse
                        .builder()
                        .statusCode(exception.getStatusCode())
                        .message(exception.getMessage())
                        .build());
    }
}
