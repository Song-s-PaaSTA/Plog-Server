package com.songspasssta.ploggingservice.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PloggingRouteRequest {

    @NotNull(message = "출발지 경도는 필수 입력 값입니다.")
    @Min(value = -180, message = "출발지 경도는 -180 이상이어야 합니다.")
    @Max(value = 180, message = "출발지 경도는 180 이하여야 합니다.")
    private Double startX;

    @NotNull(message = "출발지 위도는 필수 입력 값입니다.")
    @Min(value = -90, message = "출발지 위도는 -90 이상이어야 합니다.")
    @Max(value = 90, message = "출발지 위도는 90 이하여야 합니다.")
    private Double startY;

    @NotNull(message = "도착지 경도는 필수 입력 값입니다.")
    @Min(value = -180, message = "도착지 경도는 -180 이상이어야 합니다.")
    @Max(value = 180, message = "도착지 경도는 180 이하여야 합니다.")
    private Double endX;

    @NotNull(message = "도착지 위도는 필수 입력 값입니다.")
    @Min(value = -90, message = "도착지 위도는 -90 이상이어야 합니다.")
    @Max(value = 90, message = "도착지 위도는 90 이하여야 합니다.")
    private Double endY;

    @Min(value = -180, message = "경유지 경도는 -180 이상이어야 합니다.")
    @Max(value = 180, message = "경유지 경도는 180 이하여야 합니다.")
    private Double passX;

    @Min(value = -90, message = "경유지 위도는 -90 이상이어야 합니다.")
    @Max(value = 90, message = "경유지 위도는 90 이하여야 합니다.")
    private Double passY;

    @NotBlank(message = "요청 좌표 타입을 입력해주세요. ('EPSG3857')")
    private String reqCoordType;

    @NotBlank(message = "응답 좌표 타입을 입력해주세요. ('EPSG3857')")
    private String resCoordType;

    @NotBlank(message = "출발지 이름을 입력해주세요.")
    private String startName;

    @NotBlank(message = "도착지 이름을 입력해주세요.")
    private String endName;
}