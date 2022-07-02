package com.c8n.constants;

import lombok.Getter;

public enum CollectionStatus {
    OK(1000),
    NOT_FOUND(1101),

    NAME_IN_USE(1201),

    QUERY_NOT_RECOGNIZED(1301),

    INDEX_NOT_FOUND(1401);

    @Getter
    private final int code;

    CollectionStatus(int code) {
        this.code = code;
    }
}
