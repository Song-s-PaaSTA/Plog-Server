package com.songspasssta.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TrashResponseDto {
    private Long id;
    private Float latitude;
    private Float longitude;
    private String roadAddr;
    private String placeInfo;
}
