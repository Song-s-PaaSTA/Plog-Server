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
        final KakaoMemberResponse.Profile profile = kakaoMemberResponse.getKakaoAccount().getProfile();

        return new OauthMember(
                profile.getNickname(),
                kakaoMemberResponse.getKakaoAccount().getEmail(),
                profile.getProfileImageUrl(),
                KAKAO,
                socialLoginId
        );
    }
}
