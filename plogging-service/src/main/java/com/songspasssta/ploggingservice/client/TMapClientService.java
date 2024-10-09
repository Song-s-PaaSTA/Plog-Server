package com.songspasssta.ploggingservice.client;

import com.songspasssta.ploggingservice.config.FeignConfig;
import com.songspasssta.ploggingservice.dto.request.TMapRouteRequestWithPassList;
import com.songspasssta.ploggingservice.dto.request.TMapRouteRequestWithoutPassList;
import com.songspasssta.ploggingservice.dto.response.PloggingRouteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "tMapClientService", url = "https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1&callback=function", configuration = FeignConfig.class)
public interface TMapClientService {

    @PostMapping(consumes = "application/json", produces = "application/json")
    PloggingRouteResponse getRoute(@RequestHeader("appKey") final String appKey, @RequestBody final TMapRouteRequestWithPassList tMapRouteRequestWithPassList);

    @PostMapping(consumes = "application/json", produces = "application/json")
    PloggingRouteResponse getRoute(@RequestHeader("appKey") final String appKey, @RequestBody final TMapRouteRequestWithoutPassList tMapRouteRequestWithoutPassList);
}
