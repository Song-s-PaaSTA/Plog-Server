package com.songspasssta.reportservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * 신고글 저장 응답 DTO
 */
public record ReportListResponse(@JsonProperty("reports") List<ReportList> reports) {

    public record ReportList(Long reportId, String reportImgUrl, String reportStatus, String roadAddr, int bookmarkCount, boolean bookmarkedByUser) {
    }
}
