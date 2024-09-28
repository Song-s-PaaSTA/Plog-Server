package com.songspasssta.memberservice.dto.response;

import com.songspasssta.memberservice.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberInfoResponse {

    private final String nickname;
    private final String email;
    private final String profileImageUrl;
    private final Integer score;

    public static MemberInfoResponse of(final Member member) {
        return new MemberInfoResponse(
                member.getNickname(),
                member.getEmail(),
                member.getProfileImageUrl(),
                member.getReward().getScore()
        );
    }
}
