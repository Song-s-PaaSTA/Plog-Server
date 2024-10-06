package com.songspasssta.common.exception;

import lombok.Getter;

@Getter
public class FileOperationException extends RuntimeException {
    private final ExceptionCode exceptionCode;

    public FileOperationException(final ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public FileOperationException(final ExceptionCode exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }
}