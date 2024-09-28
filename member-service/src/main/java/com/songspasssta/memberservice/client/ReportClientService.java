package com.songspasssta.memberservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "report-service", configuration = FeignClient.class)
public interface ReportClientService {

    @DeleteMapping("/api/v1/report/{memberId}")
    void deleteAllByMemberId(@PathVariable("memberId") final Long memberId);
}