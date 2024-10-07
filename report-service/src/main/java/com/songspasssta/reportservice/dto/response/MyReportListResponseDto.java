package com.songspasssta.reportservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 특정 사용자의 신고글 내역 응답 DTO
 */

public record MyReportListResponseDto(List<MyReport> myReports) {

    public record MyReport(Long id, String reportImgUrl, String roadAddr) {
    }
}


