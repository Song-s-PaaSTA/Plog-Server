package com.songspasssta.memberservice.service;

import com.songspasssta.memberservice.domain.OauthMember;
import com.songspasssta.memberservice.dto.response.NaverMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.songspasssta.memberservice.domain.type.SocialLoginType.NAVER;

@Service
@RequiredArgsConstructor
@Transactional
public class NaverLoginService {

    private final LoginApiClient loginApiClient;

    public OauthMember login(final String accessToken) {
        final NaverMemberResponse naverMemberResponse = loginApiClient.getNaverMemberInfo("Bearer " + accessToken);
        final String socialLoginId = naverMemberResponse.getNaverMemberDetail().getId();
        final NaverMemberResponse.NaverMemberDetail memberDetail = naverMemberResponse.getNaverMemberDetail();

        return new OauthMember(
                memberDetail.getNickname(),
                NAVER,
                socialLoginId
        );
    }
}
