package com.songspasssta.ploggingservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PloggingRequest {

    @NotBlank(message = "출발지를 입력해주세요.")
    @Size(max = 100, message = "출발지는 100자 이내로 입력해주세요.")
    private String startRoadAddr;

    @NotBlank(message = "도착지를 입력해주세요.")
    @Size(max = 100, message = "도착지는 100자 이내로 입력해주세요.")
    private String endRoadAddr;

    @NotNull(message = "플로깅 시간을 입력해주세요.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime ploggingTime;
}
