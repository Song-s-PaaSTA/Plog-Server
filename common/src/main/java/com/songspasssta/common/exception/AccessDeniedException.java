package com.songspasssta.common.exception;

import lombok.Getter;

@Getter
public class AccessDeniedException extends BadRequestException {
    public AccessDeniedException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
