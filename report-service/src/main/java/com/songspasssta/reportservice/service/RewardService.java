package com.songspasssta.reportservice.service;

import com.songspasssta.common.exception.ApiClientException;
import com.songspasssta.common.exception.ExceptionCode;
import com.songspasssta.reportservice.client.RewardClient;
import com.songspasssta.reportservice.dto.response.RewardResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RewardService {
    private final RewardClient rewardClient;

    /**
     * 신고글 작성시 리워드 score를 하나 증가하는 메서드
     */
    public void increaseRewardScore(Long memberId) {
        try {
            RewardResponse rewardResponse = rewardClient.increaseScore(memberId);
            log.info("리워드 증가 성공. 회원 ID: {}, 리워드 : {}", memberId, rewardResponse.getScore());
        } catch (FeignException e) {
            log.error("리워드 증가 실패. 회원 ID: {}, 에러 메시지: {}", memberId, e.getMessage());
            throw new ApiClientException(ExceptionCode.NOT_FOUND_REWARD);
        }
    }
}