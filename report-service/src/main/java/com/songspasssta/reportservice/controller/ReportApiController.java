package com.songspasssta.reportservice.controller;

import com.songspasssta.common.response.SuccessResponse;
import com.songspasssta.reportservice.dto.request.ReportFilterRequest;
import com.songspasssta.reportservice.dto.request.ReportSaveRequest;
import com.songspasssta.reportservice.dto.request.ReportUpdateRequest;
import com.songspasssta.reportservice.dto.response.MyReportListResponse;
import com.songspasssta.reportservice.dto.response.ReportDetailResponse;
import com.songspasssta.reportservice.dto.response.ReportListResponse;
import com.songspasssta.reportservice.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.songspasssta.common.auth.GatewayConstants.GATEWAY_AUTH_HEADER;

/**
 * 신고글 API 컨트롤러 (CRUD)
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reports")
public class ReportApiController {

    private final ReportService reportService;

    /**
     * 신고글 저장
     */
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<SuccessResponse<?>> save(@RequestHeader(GATEWAY_AUTH_HEADER) Long memberId,
                                     @RequestPart("requestDto") @Valid ReportSaveRequest requestDto,
                                     @RequestPart(value = "reportImgFile", required = false) MultipartFile reportImgFile) {
        reportService.save(memberId, requestDto, reportImgFile);
        return ResponseEntity.ok().body(SuccessResponse.ofEmpty());
    }

    /**
     * 모든 신고글 조회
     */
    @GetMapping
    public ResponseEntity<SuccessResponse<ReportListResponse>> findAllReports(
            @RequestHeader(GATEWAY_AUTH_HEADER) final Long memberId,
            @RequestParam(value = "region", required = false) List<String> regions,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "status", required = false) List<String> statuses){
        ReportListResponse response = reportService.findAllReports(memberId, regions, sort, statuses);
        return ResponseEntity.ok().body(SuccessResponse.of(response));
    }

    /**
     * 신고글 상세 조회
     */
    @GetMapping("/{reportId}")
    public ResponseEntity<SuccessResponse<ReportDetailResponse>> findReportById(@PathVariable("reportId") Long reportId,
                                                                                @RequestHeader(GATEWAY_AUTH_HEADER) Long memberId) {
        ReportDetailResponse response = reportService.findReportById(reportId, memberId);
        return ResponseEntity.ok().body(SuccessResponse.of(response));
    }

    /**
     * 내 신고글 목록 조회
     */
    @GetMapping("/mine")
    public ResponseEntity<SuccessResponse<MyReportListResponse>> findMyReports(@RequestHeader(GATEWAY_AUTH_HEADER) Long memberId) {
        MyReportListResponse response = reportService.findMyReports(memberId);
        return ResponseEntity.ok().body(SuccessResponse.of(response));
    }

    /**
     * 신고글 삭제
     */
    @DeleteMapping("/{reportId}")
    public ResponseEntity<SuccessResponse<?>> deleteReport(@PathVariable("reportId") Long reportId,
                                             @RequestHeader(GATEWAY_AUTH_HEADER) Long memberId) {
        reportService.deleteReport(reportId, memberId);
        return ResponseEntity.ok().body(SuccessResponse.ofEmpty());
    }

    /**
     * 신고글 수정
     */
    @PatchMapping("/{reportId}")
    public ResponseEntity<SuccessResponse<?>> updateReport(@PathVariable("reportId") Long reportId,
                                             @RequestHeader(GATEWAY_AUTH_HEADER) Long memberId,
                                             @RequestPart("requestDto") @Valid ReportUpdateRequest requestDto,
                                             @RequestPart(value = "reportImgFile", required = false) MultipartFile reportImgFile) {
        reportService.updateReport(reportId, memberId, requestDto, reportImgFile);
        return ResponseEntity.ok().body(SuccessResponse.ofEmpty());
    }

    /**
     * 사용자 신고글 전체 삭제
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteAllByMemberId(@RequestParam("memberId") Long memberId) {
        reportService.deleteAllByMemberId(memberId);
        return ResponseEntity.noContent().build();
    }
}