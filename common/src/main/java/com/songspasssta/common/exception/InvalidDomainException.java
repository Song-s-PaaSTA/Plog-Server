package com.songspasssta.common.exception;

import lombok.Getter;

@Getter
public class InvalidDomainException extends BadRequestException {

    public InvalidDomainException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}