package com.songspasssta.common.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends BadRequestException {
    public EntityNotFoundException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
