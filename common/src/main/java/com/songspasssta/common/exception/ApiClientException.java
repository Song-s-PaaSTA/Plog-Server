package com.songspasssta.common.exception;

import lombok.Getter;

@Getter
public class ApiClientException extends RuntimeException {
    private final ExceptionCode exceptionCode;

    public ApiClientException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());  // ExceptionCode에서 제공하는 기본 메시지 사용
        this.exceptionCode = exceptionCode;
    }

    public ApiClientException(ExceptionCode exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }

    public ApiClientException(ExceptionCode exceptionCode, String message, Throwable cause) {
        super(message, cause);
        this.exceptionCode = exceptionCode;
    }
}
