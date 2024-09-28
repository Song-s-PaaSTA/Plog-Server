package com.songspasssta.memberservice.service;

import com.songspasssta.common.exception.BadRequestException;
import com.songspasssta.memberservice.domain.Reward;
import com.songspasssta.memberservice.domain.repository.RewardRepository;
import com.songspasssta.memberservice.dto.response.RewardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.songspasssta.common.exception.ExceptionCode.NOT_FOUND_REWARD;

@Service
@Transactional
@RequiredArgsConstructor
public class RewardService {

    private final RewardRepository rewardRepository;

    public RewardResponse updateScore(final Long memberId) {
        final Reward reward = rewardRepository.findByMemberId(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_REWARD));

        reward.updateScore(1);

        final Reward updatedReward = rewardRepository.save(reward);

        return RewardResponse.of(updatedReward);
    }
}
