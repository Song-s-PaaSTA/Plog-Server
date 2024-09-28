package com.songspasssta.memberservice.client;

import com.songspasssta.memberservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "report-service", configuration = FeignConfig.class)
public interface ReportClientService {

    @DeleteMapping("/api/v1/reports")
    void deleteAllByMemberId(@RequestParam("memberId") final Long memberId);
}