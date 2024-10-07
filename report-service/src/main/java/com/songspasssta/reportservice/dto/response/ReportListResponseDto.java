package com.songspasssta.reportservice.dto.response;

import com.songspasssta.reportservice.domain.Report;
import com.songspasssta.reportservice.domain.type.ReportType;
import lombok.Getter;

import java.util.List;

/**
 * 신고글 저장 응답 DTO
 */
public record ReportListResponseDto(List<ReportDto> reports) {

    public record ReportDto(Long id, String reportImgUrl, ReportType reportStatus, String roadAddr,
                            int bookmarkCount, boolean bookmarkedByUser) {
        public ReportDto(Report entity, boolean bookmarkedByUser) {
            this(entity.getId(),
                    entity.getReportImgUrl(),
                    entity.getReportType(),
                    entity.getRoadAddr(),
                    entity.getBookmarks().size(),
                    bookmarkedByUser);
        }
    }
}
