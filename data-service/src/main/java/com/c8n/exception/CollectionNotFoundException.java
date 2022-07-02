package com.c8n.exception;

public class CollectionNotFoundException extends BaseException{
    public CollectionNotFoundException(int statusCode, String collectionName) {
        super(statusCode, "Collection Not Found, name : "+collectionName);
    }
}
