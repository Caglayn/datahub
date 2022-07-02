package com.c8n.exception;

public class CollectionNameAlreadyInUse extends BaseException {
    public CollectionNameAlreadyInUse(int statusCode, String collectionName) {
        super(statusCode, "Collection name already in use, name : " + collectionName);
    }
}
