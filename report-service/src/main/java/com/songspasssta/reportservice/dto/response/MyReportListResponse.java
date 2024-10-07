package com.songspasssta.reportservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 내 신고글 조회 응답 DTO
 */

public record MyReportListResponse(@JsonProperty("myReports") List<MyReportList> myReports) {

    public record MyReportList(Long reportId, String reportImgUrl, String roadAddr) {
    }
}


