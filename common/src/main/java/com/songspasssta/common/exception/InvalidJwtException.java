package com.songspasssta.common.exception;

import lombok.Getter;

@Getter
public class InvalidJwtException extends AuthException {

    public InvalidJwtException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}