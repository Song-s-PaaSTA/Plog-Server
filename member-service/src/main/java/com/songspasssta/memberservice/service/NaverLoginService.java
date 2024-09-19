package com.songspasssta.memberservice.service;

import com.songspasssta.memberservice.config.TokenProvider;
import com.songspasssta.memberservice.domain.Member;
import com.songspasssta.memberservice.domain.RefreshToken;
import com.songspasssta.memberservice.domain.repository.MemberRepository;
import com.songspasssta.memberservice.domain.repository.RefreshTokenRepository;
import com.songspasssta.memberservice.dto.response.LoginResponse;
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
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;

    public LoginResponse login(final String accessToken) {
        final NaverMemberResponse naverMemberResponse = loginApiClient.getNaverMemberInfo("Bearer " + accessToken);
        final String socialLoginId = naverMemberResponse.getNaverMemberDetail().getId();
        final Member member = memberRepository.findBySocialLoginId(socialLoginId)
                .orElseGet(() -> {
                    final Member newMember = new Member(
                            naverMemberResponse.getNaverMemberDetail().getNickname(),
                            naverMemberResponse.getNaverMemberDetail().getEmail(),
                            naverMemberResponse.getNaverMemberDetail().getProfileImage(),
                            NAVER,
                            socialLoginId
                    );
                    return memberRepository.save(newMember);
                });

        final String newAccessToken = tokenProvider.generateAccessToken(member.getId().toString());
        final RefreshToken refreshToken = new RefreshToken(tokenProvider.generateRefreshToken(), member.getId());

        refreshTokenRepository.save(refreshToken);

        return LoginResponse.of(member, newAccessToken, refreshToken.getToken());
    }
}
