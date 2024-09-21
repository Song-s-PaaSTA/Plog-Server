package com.songspasssta.reportservice.dto.request;

import com.songspasssta.reportservice.domain.Report;
import com.songspasssta.reportservice.domain.type.ReportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportUpdateRequestDto {
    private String roadAddr;

    private ReportType reportStatus;

    private String reportDesc;

    private String reportImgUrl; // 이미지 URL

    public ReportUpdateRequestDto(String roadAddr, ReportType reportStatus, String reportDesc, String reportImgUrl) {
        this.roadAddr = roadAddr;
        this.reportStatus = reportStatus;
        this.reportDesc = reportDesc;
        this.reportImgUrl = reportImgUrl;
    }
}