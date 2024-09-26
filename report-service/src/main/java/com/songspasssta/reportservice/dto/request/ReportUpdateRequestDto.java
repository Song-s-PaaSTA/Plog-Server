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
    @NotBlank(message = "기존 이미지 url은 필수입니다.")
    private String existingImageUrl;

    @NotBlank(message = "리포트 상태는 필수입니다.")
    private String inputReportStatus;

    @NotBlank(message = "신고 설명은 필수입니다.")
    private String reportDesc;

}