package com.songspasssta.trashservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TrashResponse(@JsonProperty("trashPlaces") List<TrashDto> trashPlaces) {

    public record TrashDto(Long id, Float latitude, Float longitude, String roadAddr, String placeInfo) {
    }
}
