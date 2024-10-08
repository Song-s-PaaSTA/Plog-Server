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
import com.songspasssta.memberservice.dto.response.PloggingListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    private final BucketService bucketService;

    public LoginResponse login(final String provider, final String code) {
        if (provider.equals(KAKAO.getCode())) {
            return saveMember(kakaoLoginService.login(code));
        } else if (provider.equals(NAVER.getCode())) {
            return saveMember(naverLoginService.login(code));
        }
        throw new BadRequestException(FAIL_TO_SOCIAL_LOGIN);
    }

    private MemberInfo findOrCreateMember(final OauthMember oauthMember) {
        final Member member = memberRepository.findBySocialLoginId(oauthMember.getSocialLoginId())
                .orElseGet(() -> createMember(oauthMember));

        if (member.getNickname() == null) {
            return new MemberInfo(member, true);
        }

        return new MemberInfo(member, false);
    }

    private Member createMember(final OauthMember oauthMember) {
        final Member member = new Member(
                oauthMember.getEmail(),
                oauthMember.getSocialLoginType(),
                oauthMember.getSocialLoginId()
        );

        return memberRepository.save(member);
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

    public MemberInfoResponse completeSignup(final Long memberId, final SignupRequest signupRequest, final MultipartFile profileImage) throws IOException {
        if (profileImage == null || profileImage.isEmpty()) {
            throw new BadRequestException(PROFILE_IMAGE_EMPTY);
        }

        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER_ID));

        if (member.getNickname() != null) {
            return MemberInfoResponse.of(member);
        }

        final String profileImageUrl = bucketService.upload(profileImage);
        final Reward reward = createReward(member);

        member.updateMember(reward, signupRequest.getNickname(), profileImageUrl);

        final Member updatedMember = memberRepository.save(member);

        return MemberInfoResponse.of(updatedMember);
    }

    public MemberInfoResponse getProfile(final Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER_ID));

        return MemberInfoResponse.of(member);
    }

    public AccessTokenResponse renewAccessToken(final Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER_ID));

        final String accessToken = tokenExtractor.getAccessToken();
        final String refreshToken = tokenExtractor.getRefreshToken();
        if (tokenProvider.isValidRefreshAndValidAccess(refreshToken, accessToken)) {
            return new AccessTokenResponse(accessToken);
        } else if (tokenProvider.isValidRefreshAndInvalidAccess(refreshToken, accessToken)) {
            final String newAccessToken = tokenProvider.generateAccessToken(member.getId().toString());
            return new AccessTokenResponse(newAccessToken);
        }
        throw new BadRequestException(FAIL_TO_RENEW_ACCESS_TOKEN);
    }

    public PloggingListResponse getAllPlogging(final Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER_ID));

        final PloggingListResponse ploggingListResponse = ploggingClientService.getMemberPlogging(member.getId());
        return ploggingListResponse;
    }

    public void logout(final Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new BadRequestException(NOT_FOUND_MEMBER_ID);
        }

        final String refreshToken = tokenExtractor.getRefreshToken();
        refreshTokenRepository.deleteById(refreshToken);
    }

    public void signout(final Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER_ID));

        final String refreshToken = tokenExtractor.getRefreshToken();

        reportClientService.deleteAllByMemberId(member.getId());
        ploggingClientService.deleteAllByMemberId(member.getId());
        refreshTokenRepository.deleteById(refreshToken);
        memberRepository.deleteById(memberId);
    }

    private Reward createReward(final Member member) {
        final Reward reward = new Reward(member);

        return rewardRepository.save(reward);
    }
}
