package com.songspasssta.memberservice.dto.response;

import com.songspasssta.memberservice.domain.Reward;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RewardResponse {

    final Integer score;

    public static RewardResponse of(final Reward reward) {
        return new RewardResponse(reward.getScore());
    }
}
