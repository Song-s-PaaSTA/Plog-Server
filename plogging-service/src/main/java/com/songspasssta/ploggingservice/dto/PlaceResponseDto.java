package com.songspasssta.ploggingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceResponseDto {
    private String placName;   // 장소명
    private String roadAddress; // 도로명 주소
    private double latitude;    // 위도
    private double longitude;   // 경도
}