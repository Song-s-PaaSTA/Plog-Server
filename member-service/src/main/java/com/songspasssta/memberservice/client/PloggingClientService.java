package com.songspasssta.memberservice.client;

import com.songspasssta.memberservice.config.FeignConfig;
import com.songspasssta.memberservice.dto.response.PloggingListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "plogging-service", url = "http://plogging-service.default.svc.cluster.local:8080", configuration = FeignConfig.class)
public interface PloggingClientService {

    @GetMapping("/api/v1/plogging")
    PloggingListResponse getMemberPlogging(@RequestParam("memberId") final Long memberId);

    @DeleteMapping("/api/v1/plogging")
    void deleteAllByMemberId(@RequestParam("memberId") final Long memberId);
}
