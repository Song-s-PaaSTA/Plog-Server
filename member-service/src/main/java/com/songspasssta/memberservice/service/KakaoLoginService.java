package com.songspasssta.memberservice.service;

import com.songspasssta.memberservice.domain.OauthMember;
import com.songspasssta.memberservice.dto.response.KakaoMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.songspasssta.memberservice.domain.type.SocialLoginType.KAKAO;

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoLoginService {

    private final LoginApiClient loginApiClient;

    public OauthMember login(final String accessToken) {
        final KakaoMemberResponse kakaoMemberResponse = loginApiClient.getKakaoMemberInfo("Bearer " + accessToken);
        final String socialLoginId = kakaoMemberResponse.getId().toString();

        return new OauthMember(
                kakaoMemberResponse.getKakaoAccount().getEmail(),
                KAKAO,
                socialLoginId
        );
    }
}
