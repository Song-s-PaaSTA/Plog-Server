package com.songspasssta.reportservice.client;

import com.songspasssta.reportservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "member-service", url = "http://member-service.default.svc.cluster.local:8080", configuration = FeignConfig.class)
public interface RewardClient {

    @PatchMapping("/api/v1/reward/incr/{memberId}")
    void increaseScore(@PathVariable("memberId") Long memberId);
}