package com.songspasssta.reportservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 북마크한 신고글 응답 DTO
 */
public record BookmarkedReportsResponse(@JsonProperty("bookmarked") List<BookmarkReports> bookmarked) {

    public record BookmarkReports(Long reportId, String reportImgUrl, String reportStatus, String roadAddr, int bookmarkCount) {
    }
}
