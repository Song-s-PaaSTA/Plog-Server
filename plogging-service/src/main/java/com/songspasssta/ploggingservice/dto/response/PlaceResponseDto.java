package com.songspasssta.ploggingservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PlaceResponseDto {
    @AllArgsConstructor
    @Getter
    public static class PlaceDto {
        private Float latitude;
        private Float longitude;
        private String roadAddr;
        private String placeInfo;
    }

    private List<PlaceDto> searchedPlace;
}