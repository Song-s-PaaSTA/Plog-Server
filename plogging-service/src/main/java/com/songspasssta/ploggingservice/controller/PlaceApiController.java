package com.songspasssta.ploggingservice.controller;

import com.songspasssta.ploggingservice.dto.PlaceResponseDto;
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
@Tag(name = "Place API", description = "장소 관련 API를 제공하는 컨트롤러입니다.")
public class PlaceApiController {
    private final PlaceService placeService;

    @GetMapping
    @Operation(summary = "장소 정보 조회", description = "쿼리 문자열을 이용해 장소 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "장소 정보 조회 성공"),
    })
    public List<PlaceResponseDto> getLocationInfo(
            @Parameter(description = "검색할 장소의 쿼리 문자열", example = "Seoul")
            @RequestParam String query) {
        return placeService.getLocationInfo(query);
    }
}
