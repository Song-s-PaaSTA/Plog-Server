package com.songspasssta.reportservice.dto.request;

import com.songspasssta.reportservice.domain.Report;
import com.songspasssta.reportservice.domain.type.ReportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 신고글 저장 요청 DTO
 */
@Getter
@NoArgsConstructor
public class ReportSaveRequestDto {

    @NotNull(message = "회원 ID는 필수입니다.")
    private Long memberId;

    private String reportImgUrl;

    @NotBlank(message = "신고 설명은 필수입니다.")
    private String reportDesc;

    @NotBlank(message = "도로명 주소는 필수입니다.")
    private String roadAddr;

    // 이미지 URL을 설정하기 위한 setter 추가
    public void setReportImgUrl(String reportImgUrl) {
        this.reportImgUrl = reportImgUrl;
    }
    @Builder
    public ReportSaveRequestDto(Long memberId, String reportImgUrl, String reportDesc, String roadAddr) {
        this.memberId = memberId;
        this.reportImgUrl = reportImgUrl;
        this.reportDesc = reportDesc;
        this.roadAddr = roadAddr;
    }

    public Report toEntity() {
        return Report.builder()
                .memberId(memberId)
                .reportImgUrl(reportImgUrl)
                .reportDesc(reportDesc)
                .roadAddr(roadAddr)
                .reportStatus(ReportType.NOT_STARTED)  // 신고글 초기 상태는 NOT_STARTED
                .build();
    }
}
