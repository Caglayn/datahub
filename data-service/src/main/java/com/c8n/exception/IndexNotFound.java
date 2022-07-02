package com.c8n.exception;

public class IndexNotFound extends BaseException{
    public IndexNotFound(int statusCode, String collectionName, String columnName) {
        super(statusCode, "Index not found in collection : " + collectionName + " for column : " + columnName);
    }
}
