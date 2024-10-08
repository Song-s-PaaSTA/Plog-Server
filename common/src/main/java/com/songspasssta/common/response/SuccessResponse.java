package com.songspasssta.common.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SuccessResponse<T> {

    private final int code;
    private final T message;

    public static <T> SuccessResponse<T> of(final T message) {
        return new SuccessResponse(200, message);
    }

    public static <T> SuccessResponse<T> ofEmpty() {
        return new SuccessResponse(204, null);
    }
}