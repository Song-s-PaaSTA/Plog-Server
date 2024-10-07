package com.songspasssta.reportservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.songspasssta.reportservice.domain.Report;
import com.songspasssta.reportservice.domain.type.ReportType;
import lombok.Getter;

@Getter
public class ReportDetailResponseDto {
    @JsonProperty("reportDetail")
    private final ReportDetail reportDetail;

    @Getter
    public static class ReportDetail {
        private final Long id;
        private final String reportImgUrl;
        private final String reportDesc;
        private final String roadAddr;
        private final ReportType reportStatus;
        private final String createdAt;
        private final int bookmarkCount; // 신고글의 북마크 총 개수
        private final boolean bookmarkedByUser; // 내가 북마크했는지 여부

        public ReportDetail(Report report, boolean bookmarkedByUser) {
            this.id = report.getId();
            this.reportImgUrl = report.getReportImgUrl();
            this.reportDesc = report.getReportDesc();
            this.roadAddr = report.getRoadAddr();
            this.reportStatus = report.getReportType();
            this.createdAt = report.getCreatedAt().toString().substring(2, 10).replace("-", ".");
            this.bookmarkCount = report.getBookmarks().size();
            this.bookmarkedByUser = bookmarkedByUser;
        }
    }

    public ReportDetailResponseDto(Report report, boolean bookmarkedByUser) {
        this.reportDetail = new ReportDetail(report, bookmarkedByUser);
    }
}
