package com.songspasssta.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    INVALID_REQUEST(1000, "올바르지 않은 요청입니다."),
    INTERNAL_SEVER_ERROR(9999, "서버에서 에러가 발생하였습니다."),
    FILE_UPLOAD_ERROR(1001, "파일 업로드 중 오류가 발생하였습니다.");

    private final int code;
    private final String message;
}
