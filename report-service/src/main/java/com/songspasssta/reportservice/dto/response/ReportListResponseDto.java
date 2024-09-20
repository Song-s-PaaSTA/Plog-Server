package com.songspasssta.reportservice.dto.response;

import com.songspasssta.reportservice.domain.Report;
import com.songspasssta.reportservice.domain.type.ReportType;
import lombok.Getter;

/**
 * 신고글 저장 응답 DTO
 */
@Getter
public class ReportListResponseDto {

    private final Long id;
    private final String reportImgUrl;
    private final ReportType reportStatus;
    private final String roadAddr;
    private final int bookmarkCount; // 북마크 개수
    public ReportListResponseDto(Report entity) {
        this.id = entity.getId();
        this.reportImgUrl = entity.getReportImgUrl();
        this.reportStatus = entity.getReportStatus();
        this.roadAddr = entity.getRoadAddr();
        this.bookmarkCount = entity.getBookmarks().size();
    }
}
