package com.songspasssta.ploggingservice.controller;

import com.songspasssta.ploggingservice.dto.response.PlaceResponseDto;
import com.songspasssta.ploggingservice.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/place")
@RequiredArgsConstructor
public class PlaceApiController {
    private final PlaceService placeService;

    /**
     * 도로명 주소 기반 장소 조회
     */
    @GetMapping
    public List<PlaceResponseDto> getLocationInfo(
            @RequestParam String query) {
        return placeService.getLocationInfo(query);
    }
}
