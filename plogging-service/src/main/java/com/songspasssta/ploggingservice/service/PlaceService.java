package com.songspasssta.ploggingservice.service;

import com.songspasssta.common.exception.ExceptionCode;
import com.songspasssta.common.exception.NaverApiException;
import com.songspasssta.common.response.SuccessResponse;
import com.songspasssta.ploggingservice.client.NaverLocalSearchClient;
import com.songspasssta.ploggingservice.dto.response.NaverSearchResponse;
import com.songspasssta.ploggingservice.dto.response.PlaceResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceService {

    private final NaverLocalSearchClient naverLocalSearchClient;

    /**
     * 네이버 로컬 검색 API를 호출하여 도로명 주소 검색 결과를 받아옴
     * 검색 결과에서 필요한 장소명, 도로명 주소, 위도, 경도 정보를 추출
     */
    public ResponseEntity<SuccessResponse<PlaceResponse>> getLocationInfo(String query) {
        try {
            // 네이버 장소 검색 API 호출
            NaverSearchResponse response = naverLocalSearchClient.searchLocal(query, 5);

            // 필요한 장소 정보만 추출
            List<PlaceResponse.PlaceDto> locationInfoList = response.getItems().stream()
                    .map(item -> new PlaceResponse.PlaceDto(
                            adjustCoordinate(item.getMapy()),
                            adjustCoordinate(item.getMapx()),
                            item.getRoadAddress(),
                            // 장소 정보에 있는 불필요한 <b>와 </b>값 제거
                            StringUtils.replace(item.getTitle(), "<b>", "").replace("</b>", "")
                    )).toList();

            log.info("장소 정보 조회 성공: {}", locationInfoList);
            return ResponseEntity.ok().body(SuccessResponse.of(new PlaceResponse(locationInfoList)));
        } catch (FeignException e) {
            log.error("네이버 API 호출 실패: {}", e.getMessage(), e);
            throw new NaverApiException(ExceptionCode.NAVER_API_ERROR);
        }
    }

    // API에서 위도, 경도를 1E6 단위로 표현하는 경우를 처리
    private Double adjustCoordinate(String coordinate) {
        return Double.parseDouble(coordinate) / 1E7;
    }

}