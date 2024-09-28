package com.songspasssta.memberservice.domain.type;

import lombok.Getter;

@Getter
public enum SocialLoginType {

    NAVER("naver"),
    KAKAO("kakao");

    private final String code;

    SocialLoginType(final String code) {
        this.code = code;
    }
}
