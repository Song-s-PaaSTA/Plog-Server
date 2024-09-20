package com.songspasssta.common.exception;

import lombok.Getter;

@Getter
public class FileUploadException extends BadRequestException {
    public FileUploadException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
