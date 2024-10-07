package com.songspasssta.trashservice.dto.response;

import java.util.List;

public record TrashResponseDto(List<TrashDto> trashPlaces) {

    public record TrashDto(Long id, Float latitude, Float longitude, String roadAddr, String placeInfo) {
    }
}
