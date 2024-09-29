package com.songspasssta.ploggingservice.service;

import com.songspasssta.common.exception.ExceptionCode;
import com.songspasssta.common.exception.NaverApiException;
import com.songspasssta.ploggingservice.client.NaverLocalSearchClient;
import com.songspasssta.ploggingservice.dto.response.NaverSearchResponse;
import com.songspasssta.ploggingservice.dto.response.PlaceResponseDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceService {

    private final NaverLocalSearchClient naverLocalSearchClient;

    /**
     * 네이버 로컬 검색 API를 호출하여 도로명 주소 검색 결과를 받아옵니다.
     * 검색 결과에서 필요한 장소명, 도로명 주소, 위도, 경도 정보를 추출하여,
     * PlaceResponseDto 형태로 반환
     *
     * @param query 검색어 (도로명 주소의 일부)
     * @return 장소 정보 리스트
     */

    public List<PlaceResponseDto> getLocationInfo(String query) {
        try {
            NaverSearchResponse response = naverLocalSearchClient.searchLocal(query, 5);

            List<PlaceResponseDto> locationInfoList = response.getItems().stream()
                    .map(item -> new PlaceResponseDto(
                            item.getTitle().replaceAll("<(/)?b>", ""), // <b> 태그 제거
                            item.getRoadAddress(),
                            Double.parseDouble(item.getMapy()),
                            Double.parseDouble(item.getMapx())))
                    .collect(Collectors.toList());

            log.info(locationInfoList.toString());
            return locationInfoList;
        }  catch (FeignException e) {
            log.error("네이버 API 호출 오류: {}", e.getMessage(), e);
            throw new NaverApiException(ExceptionCode.NAVER_API_ERROR, e);
        } catch (Exception e) {
            log.error("알 수 없는 오류: {}", e.getMessage(), e);
            throw new NaverApiException(ExceptionCode.INTERNAL_SEVER_ERROR, e);
        }
    }
}