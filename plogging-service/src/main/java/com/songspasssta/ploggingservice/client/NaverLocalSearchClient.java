package com.songspasssta.ploggingservice.client;

import com.songspasssta.ploggingservice.config.NaverFeignConfig;
import com.songspasssta.ploggingservice.dto.response.NaverSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naverLocalSearchClient", url = "https://openapi.naver.com", configuration = NaverFeignConfig.class)
public interface NaverLocalSearchClient {

    @GetMapping("/v1/search/local.json")
    NaverSearchResponse searchLocal(
            @RequestParam("query") String query,
            @RequestParam(value = "display", defaultValue = "5") int display // 한 번에 표시할 검색 결과 개수: 5개 (max)
    );
}