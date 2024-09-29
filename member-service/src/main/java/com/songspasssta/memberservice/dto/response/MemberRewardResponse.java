package com.songspasssta.memberservice.dto.response;

import com.songspasssta.memberservice.domain.Reward;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberRewardResponse {

    private final String nickname;
    private final String profileImageUrl;
    private final Integer score;

    public static MemberRewardResponse of(final Reward reward) {
        return new MemberRewardResponse(
                reward.getMember().getNickname(),
                reward.getMember().getProfileImageUrl(),
                reward.getScore()
        );
    }
}
