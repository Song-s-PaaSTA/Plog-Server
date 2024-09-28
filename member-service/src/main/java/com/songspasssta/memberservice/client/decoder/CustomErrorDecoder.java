package com.songspasssta.memberservice.client.decoder;

import com.songspasssta.common.exception.BadRequestException;
import feign.Response;
import feign.codec.ErrorDecoder;

import static com.songspasssta.common.exception.ExceptionCode.INTERNAL_SERVER_ERROR;
import static com.songspasssta.common.exception.ExceptionCode.INVALID_REQUEST;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                return new BadRequestException(INVALID_REQUEST);
            default:
                return new BadRequestException(INTERNAL_SERVER_ERROR);
        }
    }
}
