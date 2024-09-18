package com.songspasssta.memberservice.service;

import com.songspasssta.memberservice.domain.Member;
import com.songspasssta.memberservice.domain.repository.MemberRepository;
import com.songspasssta.memberservice.dto.response.KakaoMemberResponse;
import com.songspasssta.memberservice.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.songspasssta.memberservice.domain.type.SocialLoginType.KAKAO;

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoLoginService {

    private final LoginApiClient loginApiClient;
    private final MemberRepository memberRepository;

    public LoginResponse login(final String accessToken) {
        final KakaoMemberResponse kakaoMemberResponse = loginApiClient.getKakaoMemberInfo("Bearer " + accessToken);
        final String socialLoginId = kakaoMemberResponse.getId().toString();
        final Member member = memberRepository.findBySocialLoginId(socialLoginId)
                .orElseGet(() -> {
                    final Member newMember = new Member(
                            kakaoMemberResponse.getKakaoAccount().getProfile().getNickname(),
                            kakaoMemberResponse.getKakaoAccount().getProfile().getEmail(),
                            kakaoMemberResponse.getKakaoAccount().getProfile().getProfileImageUrl(),
                            KAKAO,
                            socialLoginId
                    );
                    return memberRepository.save(newMember);
                });
        return LoginResponse.of(member);
    }
}
