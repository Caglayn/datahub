package com.c8n.exception;

import com.c8n.model.response.ExceptionResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CollectionNotFoundException.class)
    @Order(value = -1)
    public ResponseEntity<ExceptionResponse> handleCollectionNotFoundException(CollectionNotFoundException exception){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ExceptionResponse
                        .builder()
                        .statusCode(exception.getStatusCode())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(CollectionNameAlreadyInUse.class)
    @Order(value = -1)
    public ResponseEntity<ExceptionResponse> handleCollectionNotFoundException(CollectionNameAlreadyInUse exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse
                        .builder()
                        .statusCode(exception.getStatusCode())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(ColumnNameAlreadyInUse.class)
    @Order(value = -1)
    public ResponseEntity<ExceptionResponse> handleColumnNameAlreadyInUseException(ColumnNameAlreadyInUse exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse
                        .builder()
                        .statusCode(exception.getStatusCode())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(UnrecognizedQuery.class)
    @Order(value = -1)
    public ResponseEntity<ExceptionResponse> handleUnrecognizedQueryException(UnrecognizedQuery exception){
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(ExceptionResponse
                        .builder()
                        .statusCode(exception.getStatusCode())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(ColumnNotFound.class)
    @Order(value = -1)
    public ResponseEntity<ExceptionResponse> handleColumnNotFoundException(ColumnNotFound exception){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ExceptionResponse
                        .builder()
                        .statusCode(exception.getStatusCode())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(IndexNotFound.class)
    @Order(value = -1)
    public ResponseEntity<ExceptionResponse> handleIndexNotFoundException(IndexNotFound exception){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ExceptionResponse
                        .builder()
                        .statusCode(exception.getStatusCode())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(RowNotFound.class)
    @Order(value = -1)
    public ResponseEntity<ExceptionResponse> handleRowNotFoundException(RowNotFound exception){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ExceptionResponse
                        .builder()
                        .statusCode(exception.getStatusCode())
                        .message(exception.getMessage())
                        .build());
    }
}
