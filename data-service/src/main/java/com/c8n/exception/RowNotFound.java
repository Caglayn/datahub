package com.c8n.exception;

public class RowNotFound extends BaseException{
    public RowNotFound(int statusCode, String collectionName, String rowId) {
        super(statusCode, "Row not found in " + collectionName + " , rowId : " + rowId);
    }
}
