package com.songspasssta.common.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {
    private final int code;
    private final String message;

    public EntityNotFoundException(final ExceptionCode exceptionCode, final String message) {
        super(message);
        this.code = exceptionCode.getCode();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
