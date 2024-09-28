package com.songspasssta.memberservice.service;

import com.songspasssta.common.exception.BadRequestException;
import com.songspasssta.memberservice.client.PloggingClientService;
import com.songspasssta.memberservice.client.ReportClientService;
import com.songspasssta.memberservice.config.TokenExtractor;
import com.songspasssta.memberservice.config.TokenProvider;
import com.songspasssta.memberservice.domain.*;
import com.songspasssta.memberservice.domain.repository.MemberRepository;
import com.songspasssta.memberservice.domain.repository.RefreshTokenRepository;
import com.songspasssta.memberservice.domain.repository.RewardRepository;
import com.songspasssta.memberservice.dto.request.SignupRequest;
import com.songspasssta.memberservice.dto.response.AccessTokenResponse;
import com.songspasssta.memberservice.dto.response.LoginResponse;
import com.songspasssta.memberservice.dto.response.MemberInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.songspasssta.common.exception.ExceptionCode.*;
import static com.songspasssta.memberservice.domain.type.SocialLoginType.KAKAO;
import static com.songspasssta.memberservice.domain.type.SocialLoginType.NAVER;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final KakaoLoginService kakaoLoginService;
    private final NaverLoginService naverLoginService;
    private final MemberRepository memberRepository;
    private final RewardRepository rewardRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;
    private final TokenExtractor tokenExtractor;
    private final PloggingClientService ploggingClientService;
    private final ReportClientService reportClientService;

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

        final Member newMember = memberRepository.save(member);

        return new MemberInfo(newMember, true);
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

    public MemberInfoResponse completeSignup(final Long memberId, final SignupRequest signupRequest) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER_ID));

        // TODO Multipart 연결
        member.updateMember(signupRequest.getNickname(), null);

        return MemberInfoResponse.of(member);
    }

    public MemberInfoResponse getProfile(final Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER_ID));

        return MemberInfoResponse.of(member);
    }

    public AccessTokenResponse renewAccessToken(final Long memberId) {
        final String accessToken = tokenExtractor.getAccessToken();
        final String refreshToken = tokenExtractor.getRefreshToken();
        if (tokenProvider.isValidRefreshAndValidAccess(refreshToken, accessToken)) {
            final String newAccessToken = tokenProvider.generateAccessToken(memberId.toString());
            return new AccessTokenResponse(newAccessToken);
        } else if (tokenProvider.isValidRefreshAndInvalidAccess(refreshToken, accessToken)) {
            throw new BadRequestException(INVALID_ACCESS_TOKEN);
        }
        return new AccessTokenResponse(accessToken);
    }

    public void logout() {
        final String refreshToken = tokenExtractor.getRefreshToken();
        refreshTokenRepository.deleteById(refreshToken);
    }

    public void signout(final Long memberId) {
        reportClientService.deleteAllByMemberId(memberId);
        ploggingClientService.deleteAllByMemberId(memberId);
        memberRepository.deleteById(memberId);
    }

    // TODO 어떤 로직에 넣을지 생각 필요
    private Reward createReward(final Member member) {
        final Reward reward = new Reward(member);

        return rewardRepository.save(reward);
    }
}
