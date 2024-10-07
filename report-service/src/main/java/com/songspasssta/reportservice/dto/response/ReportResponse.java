package com.songspasssta.reportservice.dto.response;

import com.songspasssta.reportservice.domain.Report;
import com.songspasssta.reportservice.domain.type.ReportType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 신고글 저장 응답 DTO
 */
@Getter
@RequiredArgsConstructor
public class ReportResponse {
    private final Long id;
    private final Long memberId;
    private final String reportImgUrl;
    private final String reportDesc;
    private final ReportType reportStatus;
    private final String roadAddr;

    public ReportResponse(Report report) {
        this.id = report.getId();
        this.memberId = report.getMemberId();
        this.reportImgUrl = report.getReportImgUrl();
        this.reportDesc = report.getReportDesc();
        this.reportStatus = report.getReportType();
        this.roadAddr = report.getRoadAddr();
    }
}
