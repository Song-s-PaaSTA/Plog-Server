package com.songspasssta.memberservice.client;

import com.songspasssta.memberservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "plogging-service", configuration = FeignConfig.class)
public interface PloggingClientService {

    @DeleteMapping("/api/v1/plogging")
    void deleteAllByMemberId(@RequestParam("memberId") final Long memberId);
}
