package com.songspasssta.memberservice.dto.response;

import com.songspasssta.memberservice.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignupResponse {

    private final String nickname;
    private final String email;
    private final String profileImageUrl;

    public static SignupResponse of(final Member member) {
        return new SignupResponse(
                member.getNickname(),
                member.getEmail(),
                member.getProfileImageUrl()
        );
    }
}
