package com.songspasssta.reportservice.client.decoder;

import com.songspasssta.common.exception.BadRequestException;
import feign.Response;
import feign.codec.ErrorDecoder;

import static com.songspasssta.common.exception.ExceptionCode.*;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 400) {
            return new BadRequestException(INVALID_REQUEST);
        }
        return new BadRequestException(INTERNAL_SERVER_ERROR);
    }
}