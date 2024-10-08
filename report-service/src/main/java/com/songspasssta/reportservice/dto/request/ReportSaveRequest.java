package com.songspasssta.reportservice.dto.request;

import com.songspasssta.reportservice.domain.type.RegionType;
import com.songspasssta.reportservice.domain.type.ReportType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 신고글 저장 요청 DTO
 */
@Getter
@NoArgsConstructor
public class ReportSaveRequest {

    @NotBlank(message = "신고 설명은 필수입니다.")
    private String reportDesc;

    @NotBlank(message = "도로명 주소는 필수입니다.")
    private String roadAddr;

    @NotBlank(message = "신고글 상태는 필수입니다.")
    private String reportStatus;

    // 전체 도로명 주소에서 "서울" 등의 시/도 부분을 추출
    public String extractRegionFromAddr() {
        return (roadAddr != null && !roadAddr.isEmpty()) ? roadAddr.split(" ")[0] : null;
    }
}