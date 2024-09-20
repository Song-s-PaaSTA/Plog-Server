package com.songspasssta.reportservice.controller;

import com.songspasssta.reportservice.dto.request.ReportSaveRequestDto;
import com.songspasssta.reportservice.dto.response.ReportResponseDto;
import com.songspasssta.reportservice.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ReportResponseDto save(@RequestPart @Valid ReportSaveRequestDto requestDto,
                                  @RequestPart(required = false) MultipartFile reportImgFile) {
        return reportService.save(requestDto, reportImgFile);
    }
}