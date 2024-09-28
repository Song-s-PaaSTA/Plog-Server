package com.songspasssta.memberservice.service;

import com.songspasssta.memberservice.dto.response.KakaoMemberResponse;
import com.songspasssta.memberservice.dto.response.NaverMemberResponse;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public interface LoginApiClient {

    @GetExchange("https://kapi.kakao.com/v2/user/me")
    KakaoMemberResponse getKakaoMemberInfo(@RequestHeader(name = AUTHORIZATION) String bearerToken);

    @GetExchange("https://openapi.naver.com/v1/nid/me")
    NaverMemberResponse getNaverMemberInfo(@RequestHeader(name = AUTHORIZATION) String bearerToken);
}
