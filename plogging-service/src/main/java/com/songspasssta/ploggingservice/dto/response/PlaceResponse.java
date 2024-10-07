package com.songspasssta.ploggingservice.dto.response;

import java.util.List;

public record PlaceResponse(List<PlaceDto> searchedPlace) {

    public record PlaceDto(Float latitude, Float longitude, String roadAddr, String placeInfo) {
    }
}