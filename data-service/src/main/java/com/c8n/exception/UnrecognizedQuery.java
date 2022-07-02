package com.c8n.exception;

public class UnrecognizedQuery extends BaseException {
    public UnrecognizedQuery(int statusCode) {
        super(statusCode, "Could not parse query!");
    }
}
