package com.songspasssta.memberservice.dto.response;

import com.songspasssta.memberservice.domain.Reward;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class RewardListResponse {

    private final List<MemberRewardResponse> rewards;
    private final boolean hasNext;

    public static final RewardListResponse of(final Slice<Reward> rewards) {
        final List<MemberRewardResponse> memberRewardResponses = rewards.stream()
                .map(reward -> MemberRewardResponse.of(reward))
                .toList();

        return new RewardListResponse(memberRewardResponses, rewards.hasNext());
    }
}
