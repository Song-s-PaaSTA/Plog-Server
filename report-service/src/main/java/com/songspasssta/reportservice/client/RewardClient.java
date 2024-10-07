package com.songspasssta.reportservice.client;

import com.songspasssta.reportservice.config.FeignConfig;
import com.songspasssta.reportservice.dto.response.RewardResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "member-service", url = "http://localhost:54296", configuration = FeignConfig.class)
public interface RewardClient {

    @PatchMapping("/api/v1/reward/incr/{memberId}")
    RewardResponse increaseScore(@PathVariable("memberId") Long memberId);
}