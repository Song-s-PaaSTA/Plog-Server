package com.songspasssta.common.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    private final int code;
    private final String message;

    public BadRequestException(final ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage()); // 메시지를 상위 클래스에 전달
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }

    public BadRequestException(final ExceptionCode exceptionCode, Throwable cause) {
        super(exceptionCode.getMessage(), cause); // 메시지와 cause를 상위 클래스에 전달
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }
}
