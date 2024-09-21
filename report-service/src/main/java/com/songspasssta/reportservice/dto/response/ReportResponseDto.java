package com.songspasssta.reportservice.dto.response;

import com.songspasssta.reportservice.domain.Report;
import com.songspasssta.reportservice.domain.type.ReportType;
import lombok.Getter;

/**
 * 신고글 저장 응답 DTO
 */
@Getter
public class ReportResponseDto {

    private final Long id;
    private final Long memberId;
    private final String reportImgUrl;
    private final String reportDesc;
    private final ReportType reportStatus;
    private final String roadAddr;

    public ReportResponseDto(Report entity) {
        this.id = entity.getId();
        this.memberId = entity.getMemberId();
        this.reportImgUrl = entity.getReportImgUrl();
        this.reportDesc = entity.getReportDesc();
        this.reportStatus = entity.getReportType();
        this.roadAddr = entity.getRoadAddr();
    }
}
