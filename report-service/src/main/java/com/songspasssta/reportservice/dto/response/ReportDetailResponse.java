package com.songspasssta.reportservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.songspasssta.reportservice.domain.Report;
import com.songspasssta.reportservice.domain.type.ReportType;

public record ReportDetailResponse(@JsonProperty("reportDetail") ReportDetail reportDetail) {

    public ReportDetailResponse(Report report, boolean bookmarkedByUser) {
        this(new ReportDetail(
                report.getId(),
                report.getReportImgUrl(),
                report.getReportDesc(),
                report.getRoadAddr(),
                report.getReportType(),
                report.getCreatedAt().toString().substring(2, 10).replace("-", "."),
                report.getBookmarks().size(),
                bookmarkedByUser));
    }

    public record ReportDetail(Long reportId, String reportImgUrl, String reportDesc, String roadAddr,
                               ReportType reportStatus, String createdAt, int bookmarkCount, boolean bookmarkedByUser) {
    }
}

