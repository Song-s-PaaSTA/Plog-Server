package com.songspasssta.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class TrashResponseDto {
    @AllArgsConstructor
    @Getter
    public static class TrashDto {
        private Long id;
        private Float latitude;
        private Float longitude;
        private String roadAddr;
        private String placeInfo;
    }

    private List<TrashDto> trashPlaces;
}
