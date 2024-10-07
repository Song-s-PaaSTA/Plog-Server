package com.songspasssta.ploggingservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PlaceResponse(@JsonProperty("searchedPlace") List<PlaceDto> searchedPlace) {

    public record PlaceDto(Float latitude, Float longitude, String roadAddr, String placeInfo) {
    }
}