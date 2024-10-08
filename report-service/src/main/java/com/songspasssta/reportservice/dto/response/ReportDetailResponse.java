package com.songspasssta.reportservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 신고글 상세보기 응답 DTO
 */
public record ReportDetailResponse(@JsonProperty("reportDetail") ReportDetail reportDetail) {

    public record ReportDetail(Long reportId, String reportImgUrl, String reportDesc, String roadAddr, String reportStatus, String createdAt, int bookmarkCount, boolean bookmarkedByUser) {
    }
}

