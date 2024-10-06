package com.songspasssta.ploggingservice.dto.request;

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

    @NotNull(message = "출발지 경도를 입력해주세요.")
    private double startX;

    @NotNull(message = "출발지 위도를 입력해주세요.")
    private double startY;

    @NotNull(message = "도착지 경도를 입력해주세요.")
    private double endX;

    @NotNull(message = "도착지 위도를 입력해주세요.")
    private double endY;

    @NotNull(message = "경유지 경도를 입력해주세요.")
    private double passX;

    @NotNull(message = "경유지 위도를 입력해주세요.")
    private double passY;

    @NotBlank(message = "요청 좌표 타입을 입력해주세요. ('EPSG3857')")
    private String reqCoordType;

    @NotBlank(message = "응답 좌표 타입을 입력해주세요. ('EPSG3857')")
    private String resCoordType;

    @NotBlank(message = "출발지 이름을 입력해주세요.")
    private String startName;

    @NotBlank(message = "도착지 이름을 입력해주세요.")
    private String endName;
}
