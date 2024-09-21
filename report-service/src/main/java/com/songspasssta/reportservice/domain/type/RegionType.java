package com.songspasssta.reportservice.domain.type;

import lombok.Getter;

@Getter
public enum RegionType {
    // 수도권
    SEOUL("서울특별시", "서울"),
    GYEONGGI("경기도", "경기"),

    // 광역시
    BUSAN("부산광역시", "부산"),
    DAEGU("대구광역시", "대구"),
    INCHEON("인천광역시", "인천"),
    GWANGJU("광주광역시", "광주"),
    DAEJEON("대전광역시", "대전"),
    ULSAN("울산광역시", "울산"),

    // 특별자치시 및 도
    SEJONG("세종특별자치시", "세종"),
    GANGWON("강원특별자치도", "강원"),
    CHUNGBUK("충청북도", "충북"),
    CHUNGNAM("충청남도", "충남"),
    JEONBUK("전북특별자치도", "전북"),
    JEONNAM("전라남도", "전남"),
    GYEONGBUK("경상북도", "경북"),
    GYEONGNAM("경상남도", "경남"),
    JEJU("제주특별자치도", "제주");

    private final String koreanName; // 긴 도시명 (서울특별시)
    private final String shortName; // 짧은 도시명 (서울)

    RegionType(String koreanName, String shortName) {
        this.koreanName = koreanName;
        this.shortName = shortName;
    }

    // 한글 이름으로 RegionType 찾기
    public static RegionType fromKoreanName(String koreanName) {
        for (RegionType region : RegionType.values()) {
            if (region.koreanName.equals(koreanName)) {
                return region;
            }
        }
        return null;
//        throw new IllegalArgumentException("존재하지 않는 지역: " + koreanName);
    }

    // 짧은 이름으로 RegionType 찾기
    public static RegionType fromRoadAddr(String roadAddr) {
        for (RegionType region : RegionType.values()) {
            if (roadAddr.startsWith(region.shortName)) {
                return region;
            }
        }
        return null;
    }
}
