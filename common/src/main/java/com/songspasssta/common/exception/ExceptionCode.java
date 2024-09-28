package com.songspasssta.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    INVALID_REQUEST(1000, "올바르지 않은 요청입니다."),

    FILE_UPLOAD_ERROR(1001, "파일 업로드 중 오류가 발생하였습니다."),
    ENTITY_NOT_FOUND(1002, "해당 엔티티를 찾을 수 없습니다."),
    FILE_DELETE_ERROR(1003, "파일 삭제 중 오류가 발생하였습니다."),
    ACCESS_DENIED(1004, "접근(수정/삭제)할 권한이 없습니다."),

    NOT_FOUND_MEMBER_ID(2000, "존재하지 않는 회원입니다."),
    FAIL_TO_SOCIAL_LOGIN(2001, "소셜 로그인에 실패하였습니다."),
    INVALID_REFRESH_TOKEN(2002, "유효하지 않은 RefreshToken입니다."),
    INVALID_ACCESS_TOKEN(2003, "유효하지 않은 AccessToken입니다."),
    EXPIRED_REFRESH_TOKEN(2004, "만료된 AccessToken입니다."),
    EXPIRED_ACCESS_TOKEN(2005, "만료된 RefreshToken입니다."),

    NOT_FOUND_REWARD(3000, "멤버 아이디에 해당하는 리워드가 존재하지 않습니다."),

    INTERNAL_SEVER_ERROR(9999, "서버에서 에러가 발생하였습니다.");

    private final int code;
    private final String message;
}
