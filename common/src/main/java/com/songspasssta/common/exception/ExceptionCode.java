package com.songspasssta.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    INVALID_REQUEST(1000, "올바르지 않은 요청입니다."),

    FILE_UPLOAD_ERROR(1001, "파일을 업로드하는 중 오류가 발생했습니다. 다시 시도해주세요."),
    FILE_DELETE_ERROR(1002, "파일을 삭제하는 중 오류가 발생했습니다. 다시 시도해주세요."),
    BOOKMARK_NOT_FOUND(1003, "북마크를 찾을 수 없습니다."),
    REPORT_NOT_FOUND(1004, "신고글을 찾을 수 없습니다."),
    PERMISSION_DENIED(1005, "접근 권한이 없습니다."),

    NOT_FOUND_MEMBER_ID(2000, "존재하지 않는 회원입니다."),
    FAIL_TO_SOCIAL_LOGIN(2001, "소셜 로그인에 실패하였습니다."),
    INVALID_REFRESH_TOKEN(2002, "유효하지 않은 RefreshToken입니다."),
    INVALID_ACCESS_TOKEN(2003, "유효하지 않은 AccessToken입니다."),
    EXPIRED_REFRESH_TOKEN(2004, "만료된 AccessToken입니다."),
    EXPIRED_ACCESS_TOKEN(2005, "만료된 RefreshToken입니다."),
    FAIL_TO_RENEW_ACCESS_TOKEN(2006, "Access Token 재발급에 실패했습니다."),
    PROFILE_IMAGE_EMPTY(2007, "프로필 이미지 입력은 필수입니다."),

    NOT_FOUND_REWARD(3000, "리워드 증가에 실패했습니다."),

    NAVER_API_ERROR(4000, "네이버 API 호출 중 오류가 발생하였습니다."),
    INVALID_API_RESPONSE(4001, "네이버 API로부터 유효하지 않은 응답을 받았습니다."),

    INTERNAL_SERVER_ERROR(9999, "서버에서 에러가 발생하였습니다."),
    FEIGN_CLIENT_ERROR(5000, "Feign 클라이언트 호출 중 오류가 발생하였습니다."),
    REWARD_SERVICE_ERROR(5001, "리워드 서비스 호출 중 오류가 발생하였습니다.");

    private final int code;
    private final String message;
}
