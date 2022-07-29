package com.c8n.exception;

public class InvalidCredentials extends BaseException{
    public InvalidCredentials(int statusCode, String message) {
        super(statusCode, message==null ? "Bad credentials!" : message);
    }
}
