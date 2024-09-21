package com.songspasssta.reportservice.dto.request;

import com.songspasssta.reportservice.domain.Report;
import com.songspasssta.reportservice.domain.type.RegionType;
import com.songspasssta.reportservice.domain.type.ReportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 신고글 저장 요청 DTO
 */
@Getter
@NoArgsConstructor
public class ReportSaveRequestDto {

    private String reportImgUrl;

    @NotBlank(message = "신고 설명은 필수입니다.")
    private String reportDesc;

    @NotBlank(message = "도로명 주소는 필수입니다.")
    private String roadAddr;

    @NotBlank(message = "리포트 상태는 필수입니다.")
    private String inputReportStatus;

    private ReportType reportType;
    private RegionType regionType;

    // 이미지 URL을 설정하기 위한 setter 추가
    public void setReportImgUrl(String reportImgUrl) {
        this.reportImgUrl = reportImgUrl;
    }

    public void setRegionType(RegionType regionType) {
        this.regionType = regionType;
    }
    // ReportType을 설정하기 위한 setter 추가
    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    // 도로명 주소에서 "서울" 등의 시/도 부분을 추출
    public String extractRegionFromAddr() {
        if (roadAddr != null && !roadAddr.isEmpty()) {
            return roadAddr.split(" ")[0];
        }
        return null;
    }


    // Report 엔티티로 변환하는 메서드
    public Report toEntity(Long memberId) {
        return Report.builder()
                .memberId(memberId)
                .roadAddr(roadAddr)
                .reportDesc(reportDesc)
                .reportImgUrl(reportImgUrl)
                .regionType(regionType)
                .reportType(reportType)
                .build();
    }
}
