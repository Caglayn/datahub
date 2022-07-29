package com.c8n.constants;

import lombok.Getter;

public enum DhUserStatus {
    OK(1000),
    NOT_FOUND(1101),
    NAME_IN_USE(1201),
    BAD_CREDENTIALS(1202);

    @Getter
    private final int code;

    DhUserStatus(int code) {
        this.code = code;
    }
}
