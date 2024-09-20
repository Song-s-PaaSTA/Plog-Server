package com.songspasssta.reportservice.controller;

import com.songspasssta.reportservice.dto.request.ReportSaveRequestDto;
import com.songspasssta.reportservice.dto.response.MyReportListResponseDto;
import com.songspasssta.reportservice.dto.response.ReportDetailResponseDto;
import com.songspasssta.reportservice.dto.response.ReportListResponseDto;
import com.songspasssta.reportservice.dto.response.ReportResponseDto;
import com.songspasssta.reportservice.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 신고글 API 컨트롤러
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reports")
public class ReportApiController {

    private final ReportService reportService;

    /**
     * 신고글 저장
     *
     * @param requestDto     신고글 저장 요청 DTO
     * @param reportImgFile  신고 이미지 파일
     * @return ReportResponseDto 신고글 응답 DTO, 201 Created를 반환
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReportResponseDto save(@RequestPart("requestDto") @Valid ReportSaveRequestDto requestDto,
                                  @RequestPart(value = "reportImgFile", required = false) MultipartFile reportImgFile) {
        // TODO request header로 토큰 받기
        return reportService.save(requestDto, reportImgFile);
    }
    /**
     * 모든 신고글 조회
     *
     * @return List<ReportResponseDto> 신고글 목록
     */
    @GetMapping
    public List<ReportListResponseDto> findAllReports(@RequestParam("memberId") Long memberId) {
        // TODO request header로 토큰 받기
        return reportService.findAllReports(memberId);
    }

    /**
     * 신고글 상세 조회
     *
     * @param reportId 신고글 ID
     * @param memberId 회원 ID
     * @return ReportDetailResponseDto 신고글 상세 정보
     */
    @GetMapping("/{reportId}")
    public ReportDetailResponseDto findReportById(@PathVariable("reportId") Long reportId,
                                                  @RequestParam("memberId") Long memberId) {
        // TODO request header로 토큰 받기
        return reportService.findReportById(reportId, memberId);
    }

    /**
     * 특정 사용자의 신고글 내역 조회
     *
     * @param memberId 현재 로그인된 사용자 ID
     * @return List<MyReportListResponseDto> 신고글 목록
     */
    @GetMapping("/mine")
    @ResponseStatus(HttpStatus.OK)
    public List<MyReportListResponseDto> findMyReports(@RequestParam("memberId") Long memberId) {
        return reportService.findMyReports(memberId);
    }
}