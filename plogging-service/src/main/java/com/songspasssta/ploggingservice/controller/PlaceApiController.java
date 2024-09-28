package com.songspasssta.ploggingservice.controller;

import com.songspasssta.ploggingservice.dto.PlaceResponseDto;
import com.songspasssta.ploggingservice.service.PlaceService;
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

    @GetMapping
    public List<PlaceResponseDto> getLocationInfo(@RequestParam String query) {
        return placeService.getLocationInfo(query);
    }
}
