package com.c8n.exception;

import lombok.Getter;

public abstract class BaseException extends RuntimeException {
    @Getter
    private int statusCode;

    public BaseException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
