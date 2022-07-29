package com.c8n.exception;

public class DhUserNotFound extends BaseException{
    public DhUserNotFound(int statusCode, String userName) {
        super(statusCode, "User not found username : " + userName);
    }
}
