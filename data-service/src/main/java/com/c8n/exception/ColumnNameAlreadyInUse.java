package com.c8n.exception;

public class ColumnNameAlreadyInUse extends BaseException {

    public ColumnNameAlreadyInUse(int statusCode, String columnName) {
        super(statusCode, "Column name already in use for this collection, name : " + columnName);
    }
}
