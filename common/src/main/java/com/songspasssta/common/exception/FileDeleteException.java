package com.songspasssta.common.exception;

import lombok.Getter;

@Getter
public class FileDeleteException extends BadRequestException {
    public FileDeleteException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
