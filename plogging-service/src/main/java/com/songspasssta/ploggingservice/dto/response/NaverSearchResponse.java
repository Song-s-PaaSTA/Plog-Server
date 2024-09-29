package com.songspasssta.ploggingservice.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class NaverSearchResponse {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<NaverSearchItem> items;

    @Getter
    public static class NaverSearchItem {
        private String title;       // 장소명
        private String address;     // 지번 주소
        private String roadAddress; // 도로명 주소
        private String mapx;        // 경도
        private String mapy;        // 위도
    }
}
