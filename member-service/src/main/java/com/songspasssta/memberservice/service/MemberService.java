package com.songspasssta.memberservice.service;

import com.songspasssta.common.exception.BadRequestException;
import com.songspasssta.memberservice.config.TokenProvider;
import com.songspasssta.memberservice.domain.Member;
import com.songspasssta.memberservice.domain.MemberInfo;
import com.songspasssta.memberservice.domain.OauthMember;
import com.songspasssta.memberservice.domain.RefreshToken;
import com.songspasssta.memberservice.domain.repository.MemberRepository;
import com.songspasssta.memberservice.domain.repository.RefreshTokenRepository;
import com.songspasssta.memberservice.dto.request.SignupRequest;
import com.songspasssta.memberservice.dto.response.LoginResponse;
import com.songspasssta.memberservice.dto.response.SignupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.songspasssta.common.exception.ExceptionCode.FAIL_TO_SOCIAL_LOGIN;
import static com.songspasssta.common.exception.ExceptionCode.NOT_FOUND_MEMBER_ID;
import static com.songspasssta.memberservice.domain.type.SocialLoginType.KAKAO;
import static com.songspasssta.memberservice.domain.type.SocialLoginType.NAVER;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final KakaoLoginService kakaoLoginService;
    private final NaverLoginService naverLoginService;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;

    public LoginResponse login(final String provider, final String code) {
        if (provider.equals(KAKAO.getCode())) {
            return saveMember(kakaoLoginService.login(code));
        } else if (provider.equals(NAVER.getCode())) {
            return saveMember(naverLoginService.login(code));
        }
        throw new BadRequestException(FAIL_TO_SOCIAL_LOGIN);
    }

    private MemberInfo findOrCreateMember(final OauthMember oauthMember) {
        return memberRepository.findBySocialLoginId(oauthMember.getSocialLoginId())
                .map(member -> new MemberInfo(member, false))
                .orElseGet(() -> createMember(oauthMember));
    }

    private MemberInfo createMember(final OauthMember oauthMember) {
        final Member member = new Member(
                oauthMember.getNickname(),
                oauthMember.getEmail(),
                oauthMember.getProfileImageUrl(),
                oauthMember.getSocialLoginType(),
                oauthMember.getSocialLoginId()
        );

        return new MemberInfo(member, true);
    }

    private LoginResponse saveMember(final OauthMember oauthMember) {
        final MemberInfo memberInfo = findOrCreateMember(oauthMember);
        final Long memberId = memberInfo.getMember().getId();

        final String accessToken = tokenProvider.generateAccessToken(memberId.toString());
        final RefreshToken refreshToken = new RefreshToken(tokenProvider.generateRefreshToken(), memberId);

        refreshTokenRepository.save(refreshToken);

        return LoginResponse.of(
                memberInfo.getMember(),
                accessToken,
                refreshToken.getToken(),
                memberInfo.getIsNewMember()
        );
    }

    public SignupResponse completeSignup(final Long memberId, final SignupRequest signupRequest) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER_ID));

        // TODO Multipart 연결
        member.updateMember(signupRequest.getNickname(), null);

        return SignupResponse.of(member);
    }
}
