package com.songspasssta.common.exception;

import lombok.Getter;

@Getter
public class ApiClientException extends RuntimeException {
    private final ExceptionCode exceptionCode;

    public ApiClientException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public int getCode() {
        return exceptionCode.getCode(); // 예외 코드 반환
    }

    public String getMessage() {
        return exceptionCode.getMessage(); // 예외 메시지 반환
    }
}
