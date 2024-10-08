package com.songspasssta.ploggingservice.controller;

import com.songspasssta.common.response.SuccessResponse;
import com.songspasssta.ploggingservice.dto.response.PlaceResponse;
import com.songspasssta.ploggingservice.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/place")
@RequiredArgsConstructor
public class PlaceApiController {
    private final PlaceService placeService;

    /**
     * 도로명 주소 기반 장소 조회
     */
    @GetMapping
    public ResponseEntity<SuccessResponse<PlaceResponse>> getLocationInfo(@RequestParam String query) {
        return placeService.getLocationInfo(query);
    }
}
