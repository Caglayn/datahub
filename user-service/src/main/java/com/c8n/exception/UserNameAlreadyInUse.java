package com.c8n.exception;

public class UserNameAlreadyInUse extends BaseException {
    public UserNameAlreadyInUse(int statusCode, String userName) {
        super(statusCode, "User name already in use, name : " + userName);
    }
}
