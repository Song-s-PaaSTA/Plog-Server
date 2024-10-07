package com.songspasssta.trashservice.dto.response;

import java.util.List;

public record TrashResponse(List<TrashDto> trashPlaces) {

    public record TrashDto(Long id, Float latitude, Float longitude, String roadAddr, String placeInfo) {
    }
}
