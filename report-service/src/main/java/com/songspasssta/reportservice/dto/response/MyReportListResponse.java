package com.songspasssta.reportservice.dto.response;

import java.util.List;

/**
 * 특정 사용자의 신고글 내역 응답 DTO
 */

public record MyReportListResponse(List<MyReport> myReports) {

    public record MyReport(Long id, String reportImgUrl, String roadAddr) {
    }
}


