package com.songspasssta.reportservice.dto.response;

import com.songspasssta.reportservice.domain.Report;
import lombok.Getter;

/**
 * 특정 사용자의 신고글 내역 응답 DTO
 */
@Getter
public class MyReportListResponseDto {

    private final Long reportId;
    private final String reportImgUrl;
    private final String roadAddr;

    public MyReportListResponseDto(Report report) {
        this.reportId = report.getId();
        this.reportImgUrl = report.getReportImgUrl();
        this.roadAddr = report.getRoadAddr();
    }
}

