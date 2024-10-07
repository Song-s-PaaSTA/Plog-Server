package com.songspasssta.ploggingservice.service;

import com.songspasssta.common.exception.ExceptionCode;
import com.songspasssta.common.exception.NaverApiException;
import com.songspasssta.ploggingservice.client.NaverLocalSearchClient;
import com.songspasssta.ploggingservice.dto.response.NaverSearchResponse;
import com.songspasssta.ploggingservice.dto.response.PlaceResponseDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceService {

    private final NaverLocalSearchClient naverLocalSearchClient;

    /**
     * 네이버 로컬 검색 API를 호출하여 도로명 주소 검색 결과를 받아옴
     * 검색 결과에서 필요한 장소명, 도로명 주소, 위도, 경도 정보를 추출
     */
    public ResponseEntity<PlaceResponseDto> getLocationInfo(String query) {
        try {
            NaverSearchResponse response = naverLocalSearchClient.searchLocal(query, 5);

            List<PlaceResponseDto.PlaceDto> locationInfoList = response.getItems().stream()
                    .map(item -> new PlaceResponseDto.PlaceDto(
                            Float.parseFloat(item.getMapy()),
                            Float.parseFloat(item.getMapx()),
                            item.getRoadAddress(),
                            StringUtils.replace(item.getTitle(), "<b>", "").replace("</b>", "")) // <b> 태그 제거
                    ).collect(Collectors.toList());

            PlaceResponseDto placeResponse = new PlaceResponseDto(locationInfoList);
            log.info("장소 정보 조회 성공: {}", locationInfoList);
            return ResponseEntity.ok(placeResponse);
        } catch (FeignException e) {
            log.error("네이버 API 호출 실패: {}", e.getMessage(), e);
            throw new NaverApiException(ExceptionCode.NAVER_API_ERROR);
        }
    }
}