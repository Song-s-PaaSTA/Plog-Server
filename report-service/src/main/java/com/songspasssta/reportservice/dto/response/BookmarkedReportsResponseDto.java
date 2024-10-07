package com.songspasssta.reportservice.dto.response;

import java.util.List;

/**
 * 신고글 저장 응답 DTO
 */
public record BookmarkedReportsResponseDto(List<BookmarkSummaryDto> bookmarked) {

    public record BookmarkSummaryDto(Long id, String reportImgUrl, ReportStatusDto reportStatus, String roadAddr,
                                     int bookmarkCount, boolean bookmarkedByUser) {

        public record ReportStatusDto(String type) {
        }
    }
}
