package com.c8n.exception;

public class ColumnNotFound extends BaseException{
    public ColumnNotFound(int statusCode, String collectionName, String columnName) {
        super(statusCode, "Column " + columnName + " not found in collection " + collectionName);
    }
}
