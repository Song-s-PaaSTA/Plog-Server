package com.songspasssta.reportservice.controller;

import com.songspasssta.reportservice.dto.request.ReportSaveRequestDto;
import com.songspasssta.reportservice.dto.request.ReportUpdateRequestDto;
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
     * @param requestDto    신고글 저장 요청 DTO
     * @param reportImgFile 신고 이미지 파일
     * @return ReportResponseDto 신고글 응답 DTO, 201 Created를 반환
     */
    @PostMapping(consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public ReportResponseDto save(@RequestParam("memberId") Long memberId,
                                  @RequestPart("requestDto") @Valid ReportSaveRequestDto requestDto,
                                  @RequestPart(value = "reportImgFile", required = false) MultipartFile reportImgFile) {
        // TODO request header로 토큰 받기
        return reportService.save(memberId, requestDto, reportImgFile);
    }

    /**
     * 모든 신고글 조회
     *
     * @param memberId 현재 로그인된 사용자 ID
     * @param regions  (선택) 조회할 지역. 예: "서울특별시"
     * @param sort     (선택) 정렬 기준 리스트. 예: ["like", "date"] (인기순, 최신순)
     * @param statuses (선택) 신고글의 상태. 예: "준비중", "완료"
     * @return List<ReportListResponseDto> 필터링 및 정렬된 신고글 목록
     */
    @GetMapping
    public List<ReportListResponseDto> findAllReports(
            @RequestParam("memberId") Long memberId,
            @RequestParam(value = "region", required = false) List<String> regions, // 지역 필터링
            @RequestParam(value = "sort", required = false) String sort, // 정렬 기준, 단일 값만 허용
            @RequestParam(value = "status", required = false) List<String> statuses // 상태 필터링
    ) {
        // TODO request header로 토큰 받기
        return reportService.findAllReports(memberId, regions, sort, statuses);
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

    /**
     * 신고글 삭제
     *
     * @param reportId 삭제할 신고글 ID
     * @param memberId 현재 로그인된 사용자 ID
     */
    @DeleteMapping("/{reportId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReport(@PathVariable("reportId") Long reportId,
                             @RequestParam("memberId") Long memberId) {
        reportService.deleteReport(reportId, memberId);
    }

    /**
     * 신고글 수정
     *
     * @param reportId      수정할 신고글 ID
     * @param requestDto    신고글 수정 요청 DTO
     * @param reportImgFile 수정할 신고 이미지 파일
     * @return ReportResponseDto 수정된 신고글 응답 DTO
     */
    @PatchMapping("/{reportId}")
    @ResponseStatus(HttpStatus.OK)
    public ReportResponseDto updateReport(@PathVariable("reportId") Long reportId,
                                          @RequestParam("memberId") Long memberId,
                                          @RequestPart(value = "requestDto") @Valid ReportUpdateRequestDto requestDto,
                                          @RequestPart(value = "reportImgFile", required = false) MultipartFile reportImgFile) { // 새 이미지는 필수 아님
        // TODO request header로 토큰 받기
        return reportService.updateReport(reportId, memberId, requestDto, reportImgFile);
    }
}