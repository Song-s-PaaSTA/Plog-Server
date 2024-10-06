package com.songspasssta.common.exception;

import lombok.Getter;

@Getter
public class PermissionDeniedException extends RuntimeException {
    private final ExceptionCode exceptionCode;

    public PermissionDeniedException(final ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public PermissionDeniedException(final ExceptionCode exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }
}
