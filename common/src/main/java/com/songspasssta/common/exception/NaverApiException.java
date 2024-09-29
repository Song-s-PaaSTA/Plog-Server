package com.songspasssta.common.exception;

import lombok.Getter;

@Getter
public class NaverApiException extends BadRequestException {
    public NaverApiException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
    public NaverApiException(final ExceptionCode exceptionCode, Throwable cause) {
        super(exceptionCode, cause); // 부모 클래스의 cause를 받는 생성자 호출
    }
}