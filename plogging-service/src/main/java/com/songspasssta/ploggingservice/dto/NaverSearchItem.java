package com.songspasssta.ploggingservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverSearchItem {
    private String title;       // 장소명
    private String address;     // 지번 주소
    private String roadAddress; // 도로명 주소
    private String mapx;        // 경도
    private String mapy;        // 위도
}